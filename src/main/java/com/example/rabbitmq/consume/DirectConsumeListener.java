package com.example.rabbitmq.consume;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Slf4j
@Configuration
public class DirectConsumeListener {

    /**
     * 监听指定队列，名称：mq.direct.1
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = "mq.direct.1")
    public void consume(Message message, Channel channel) throws IOException {
        log.info("DirectConsumeListener，收到消息: {}", message.toString());
    }
}
