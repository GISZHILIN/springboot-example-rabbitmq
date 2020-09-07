package com.example.rabbitmq.entity;

import java.io.Serializable;

public class ConsumerInfo implements Serializable {

    private static final long serialVersionUID = 1l;

    private String queueName;

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
