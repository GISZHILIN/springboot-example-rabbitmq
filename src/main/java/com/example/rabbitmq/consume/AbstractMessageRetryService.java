package com.example.rabbitmq.consume;

import com.example.rabbitmq.entity.MessageRetryDto;
import com.example.rabbitmq.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public abstract class AbstractMessageRetryService {

    private final static String CONTENT_TYPE = "application/json";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void initMessage(Message message) {
        log.info("收到消息: {}", message.toString());
        try {
            //封装消息
            MessageProperties messageProperties = message.getMessageProperties();
            String msg = new String(message.getBody(), "UTF-8");
            MessageRetryDto messageRetryDto = warpMessageRetryInfo(messageProperties.getReceivedExchange(), messageProperties.getReceivedRoutingKey(), messageProperties.getConsumerQueue(), msg);
            Thread.sleep(messageRetryDto.getRetryIntervalTime());
            prepareAction(messageRetryDto);
        } catch (Exception e) {
            log.warn("处理消息异常，错误信息：", e);
        }
    }

    /**
     * 准备执行
     * @param retryDto
     */
    private void prepareAction(MessageRetryDto retryDto){
        try {
            doAction(retryDto.getBizData());
            doSuccessCallBack(retryDto);
        } catch (Exception e) {
            //执行失败，计算是否还需要继续重试
            if(retryDto.checkRetryCount()){
                Message message = getMessage(retryDto);
                rabbitTemplate.convertAndSend(retryDto.getExchangeName(), retryDto.getRoutingKey(), message);
            }else{
                log.warn("当前任务重试次数超过最大值，任务队列：{}，失败原因：{}", retryDto.getQueueName(), e);
                doFailCallBack(retryDto.setErrorMsg(e.getMessage()));
            }
        }
    }

    /**
     * 执行任务
     * @param dataJson
     */
    protected abstract void doAction(String dataJson);

    /**
     * 任务执行成功，回调服务
     * @param messageRetryDto
     */
    private void doSuccessCallBack(MessageRetryDto messageRetryDto){
        try {
            successCallback(messageRetryDto);
        } catch (Exception e) {
            log.warn("执行成功回调异常，错误原因：{}", e.getMessage());
        }
    }

    /**
     * 任务执行失败，回调服务
     * @param messageRetryDto
     */
    private void doFailCallBack(MessageRetryDto messageRetryDto){
        try {
            failCallback(messageRetryDto);
        } catch (Exception e) {
            log.warn("执行失败回调异常，错误原因：{}", e.getMessage());
        }
    }

    protected abstract void successCallback(MessageRetryDto messageRetryDto);

    protected abstract void failCallback(MessageRetryDto messageRetryDto);

    /**
     * 包装消息重试对象
     * @param exchange
     * @param routeKey
     * @param queueName
     * @param msg
     * @return
     */
    private MessageRetryDto warpMessageRetryInfo(String exchange, String routeKey, String queueName, String msg){
        MessageRetryDto entity = JsonUtils.fromJson(msg, MessageRetryDto.class);
        entity.setExchangeName(exchange);
        entity.setRoutingKey(routeKey);
        entity.setQueueName(queueName);
        return entity;
    }

    /**
     * 封装系统消息对象
     * @param msg
     * @return
     */
    private Message getMessage(Object msg){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(CONTENT_TYPE);
        Message message = new Message(JsonUtils.toJson(msg).getBytes(),messageProperties);
        return message;
    }
}
