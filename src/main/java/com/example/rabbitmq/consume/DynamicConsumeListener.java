package com.example.rabbitmq.consume;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DynamicConsumeListener {

    /**
     * 使用SimpleMessageListenerContainer实现动态监听
     * @param connectionFactory
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setMessageListener((MessageListener) message -> {
            log.info("ConsumerMessageListen，收到消息: {}", message.toString());
        });
        return container;
    }
}
