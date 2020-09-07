package com.example.rabbitmq.web;

import com.alibaba.fastjson.JSONObject;
import com.example.rabbitmq.config.RabbitUtil;
import com.example.rabbitmq.entity.MessageRetryDto;
import com.example.rabbitmq.entity.ProduceInfo;
import com.example.rabbitmq.util.JsonUtils;
import com.google.gson.JsonObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produce")
public class ProduceController {

    @Autowired
    private RabbitUtil rabbitUtil;

    /**
     * 发送消息到交换器
     * @param produceInfo
     */
    @PostMapping("sendMessage")
    public void sendMessage(@RequestBody ProduceInfo produceInfo) {
        rabbitUtil.convertAndSend(produceInfo.getExchangeName(), produceInfo.getRoutingKey(), produceInfo.getMsg());
    }

    @PostMapping("sendMessageInfo")
    public void sendMessageInfo(ProduceInfo produceInfo) {
        JSONObject jsonObject = JSONObject.parseObject(produceInfo.msg);
        rabbitUtil.convertAndSend(produceInfo.getExchangeName(), produceInfo.getRoutingKey(), jsonObject);
    }

    @PostMapping("sendRetryMessage")
    public void sendRetryMessage(@RequestBody ProduceInfo produceInfo) {
        MessageRetryDto entity = new MessageRetryDto();
        entity.setMaxTryCount(5);
        entity.setBizData(JsonUtils.toJson(produceInfo));
        rabbitUtil.convertAndSend(produceInfo.getExchangeName(), produceInfo.getRoutingKey(), entity);
    }

    @PostMapping("sendMessageHeaderAll")
    public void sendMessageHeaderAll(@RequestBody ProduceInfo produceInfo) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("queue", "queue");
        messageProperties.setHeader("bindType", "whereAll");
        Message message = new Message(produceInfo.toString().getBytes(), messageProperties);
        rabbitUtil.convertAndSend(produceInfo.getExchangeName(), null, message);
    }

    @PostMapping("sendMessageHeaderAny")
    public void HeaderAny(@RequestBody ProduceInfo produceInfo) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("queue", "queue2");
        messageProperties.setHeader("bindType", "whereAny");
        Message message = new Message(produceInfo.toString().getBytes(), messageProperties);
        rabbitUtil.convertAndSend(produceInfo.getExchangeName(), null, message);
    }

}
