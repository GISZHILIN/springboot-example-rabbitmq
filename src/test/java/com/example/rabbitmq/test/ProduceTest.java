package com.example.rabbitmq.test;

import com.example.rabbitmq.config.RabbitUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProduceTest {

    @Autowired
    private RabbitUtil rabbitUtil;

    @Test
    public void testAndExchangeBindingQueue(){
        rabbitUtil.andExchangeBindingQueue(RabbitUtil.ExchangeType.DIRECT, "ex.direct.exchange", "ex.fanout.exchange", "routekey", false, null);
    }

    @Test
    public void testSend(){
        for (int i = 0; i < 10; i++) {
            rabbitUtil.convertAndSend("ex.fanout.exchange", null, "hello world" + i);
        }
    }
}
