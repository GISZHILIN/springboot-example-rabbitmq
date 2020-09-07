package com.example.rabbitmq.consume;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class TopicConsumeListener {

    private static final Logger logger = LoggerFactory.getLogger(TopicConsumeListener.class);

    @RabbitListener(queues = "mq.topic.1")
    public void consume(Message message, Channel channel) throws IOException {
        logger.info("TopicConsumeListener，收到消息: {}", message.toString());
    }
}
