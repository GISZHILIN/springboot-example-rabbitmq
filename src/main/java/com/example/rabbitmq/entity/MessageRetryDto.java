package com.example.rabbitmq.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MessageRetryDto implements Serializable {

    /**
     * 消息来源ID
     */
    private String sourceId;

    /**
     * 消息来源描述
     */
    private String sourceDesc;

    /**
     * 交换器
     */
    private String exchangeName;

    /**
     * 路由键
     */
    private String routingKey;

    /**
     * 队列
     */
    private String queueName;

    /**
     * 最大重试次数
     */
    private Integer maxTryCount = 3;

    /**
     * 当前重试次数
     */
    private Integer currentRetryCount = 0;

    /**
     * 重试时间间隔（毫秒）
     */
    private Long retryIntervalTime = 1000L;

    /**
     * 业务数据
     */
    private String bizData;

    /**
     * 任务失败信息
     */
    private String errorMsg;

    /**
     * 检查重试次数是否超过最大值
     * @return
     */
    public boolean checkRetryCount(){
        retryCountCalculate();
        //检查重试次数是否超过最大值
        if(this.currentRetryCount < this.maxTryCount){
            return true;
        }
        return false;
    }

    /**
     * 状态枚举
     */
    public static class MessageRetryStatus{

        /**
         * 初始化
         */
        public static final Integer INIT = 1;

        /**
         * 执行成功
         */
        public static final Integer SUCCESS = 2;

        /**
         * 执行失败
         */
        public static final Integer FAIL = 3;
    }

    /**
     * 重新计算重试次数
     */
    private void retryCountCalculate(){
        this.currentRetryCount = this.currentRetryCount + 1;
    }

}
