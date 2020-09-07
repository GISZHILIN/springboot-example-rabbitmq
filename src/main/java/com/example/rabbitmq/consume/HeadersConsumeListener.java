package com.example.rabbitmq.consume;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class HeadersConsumeListener {

    private static final Logger logger = LoggerFactory.getLogger(HeadersConsumeListener.class);

    @RabbitListener(queues = "mq.header.1")
    public void consume(Message message, Channel channel) throws IOException {
        logger.info("HeadersConsumeListener，收到消息: {}", message.toString());
    }
}
