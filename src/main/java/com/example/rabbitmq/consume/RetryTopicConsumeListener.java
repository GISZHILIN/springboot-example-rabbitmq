package com.example.rabbitmq.consume;

import com.example.rabbitmq.entity.MessageRetryDto;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class RetryTopicConsumeListener extends AbstractMessageRetryService {

    private static final Logger logger = LoggerFactory.getLogger(RetryTopicConsumeListener.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "mq.topic.2")
    public void consume(Message message, Channel channel) throws IOException, InterruptedException {
        super.initMessage(message);
    }

    @Override
    protected void doAction(String dataJson) {
        logger.info("业务参数：{}", dataJson);
//        throw new CommonExecption("hello");
    }

    @Override
    protected void successCallback(MessageRetryDto messageRetryDto) {
        logger.info("成功回调：{}", messageRetryDto.getBizData());
    }

    @Override
    protected void failCallback(MessageRetryDto messageRetryDto) {
        logger.info("失败回调：{}", messageRetryDto.getBizData());
    }


}
