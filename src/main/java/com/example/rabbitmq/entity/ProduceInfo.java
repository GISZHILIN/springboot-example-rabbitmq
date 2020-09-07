package com.example.rabbitmq.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProduceInfo implements Serializable {

    private static final long serialVersionUID = 1l;

    /**
     * 交换器名称
     */
    private String exchangeName;

    /**
     * 路由键key
     */
    private String routingKey;

    /**
     * 消息内容
     */
    public String msg;
}
