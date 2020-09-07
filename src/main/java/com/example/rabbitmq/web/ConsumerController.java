package com.example.rabbitmq.web;

import com.alibaba.fastjson.JSONArray;
import com.example.rabbitmq.config.RabbitUtil;
import com.example.rabbitmq.core.execption.CommonExecption;
import com.example.rabbitmq.entity.ConsumerInfo;
import com.example.rabbitmq.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    private SimpleMessageListenerContainer container;

    @Autowired
    private RabbitUtil rabbitUtil;

    /**
     * 添加队列到监听器
     * @param consumerInfo
     */
    @PostMapping("addQueue")
    public void addQueue(@RequestBody ConsumerInfo consumerInfo) {
        boolean existQueue = rabbitUtil.existQueue(consumerInfo.getQueueName());
        if(!existQueue){
            throw new CommonExecption("当前队列不存在");
        }
        //添加队列到监听器
        container.addQueueNames(consumerInfo.getQueueName());
        //打印监听容器中正在监听到队列
        log.info("container-queue:{}", JsonUtils.toJson(container.getQueueNames()));
    }

    /**
     * 移除正在监听的队列
     * @param consumerInfo
     */
    @PostMapping("removeQueue")
    public void removeQueue(@RequestBody ConsumerInfo consumerInfo) {
        //从监听器中移除队列
        container.removeQueueNames(consumerInfo.getQueueName());
        //打印监听容器中正在监听到队列
        log.info("container-queue:{}", JsonUtils.toJson(container.getQueueNames()));
    }

    /**
     * 查询监听容器中正在监听到队列
     */
    @PostMapping("queryListenerQueue")
    public void queryListenerQueue() {
        log.info("container-queue:{}", JsonUtils.toJson(container.getQueueNames()));
    }
}
