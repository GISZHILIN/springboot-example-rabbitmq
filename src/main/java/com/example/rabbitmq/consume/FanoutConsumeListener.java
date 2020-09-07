package com.example.rabbitmq.consume;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Slf4j
@Configuration
public class FanoutConsumeListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "mq.fanout.1")
    public void consume(Message message, Channel channel) throws IOException {
        String msgJson = new String(message.getBody(),"UTF-8");
        log.info("FanoutConsumeListener，收到消息: {}", message.toString());
//        rabbitTemplate.convertAndSend(message.getMessageProperties().getReceivedExchange(), message.getMessageProperties().getReceivedRoutingKey(), msgJson);
    }
}
