package com.mytest.mqandspring.mq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PushConsumer {

    private static Logger logger = LoggerFactory.getLogger("PushConsumer");

    private String topic;

    private String consumerGroupName;

    private String namesrvAddr;

    private String instanceName;

    private MessageListenerConcurrently messageListenerConcurrently;

    private DefaultMQPushConsumer consumer;

    public void init() throws MQClientException {
        logger.info("[PushConsumer 开始初始化消费者]");
        consumer = new DefaultMQPushConsumer(consumerGroupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumerGroup(consumerGroupName);
        consumer.setInstanceName(instanceName);
        consumer.subscribe(topic, "*");//只接收topic下tag为192.168.100.200的消息
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);//程序第一次启动从队列的【最后】位置开始消费
        consumer.registerMessageListener(messageListenerConcurrently);
        consumer.setVipChannelEnabled(false);//关闭vip通道 监听端口10909（默认为true）
        consumer.start();
        logger.info("[PushConsumer 初始化消费者成功]");
    }

    public void destroy() throws MQClientException{
        consumer.shutdown();
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getConsumerGroupName() {
        return consumerGroupName;
    }

    public void setConsumerGroupName(String consumerGroupName) {
        this.consumerGroupName = consumerGroupName;
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public MessageListenerConcurrently getMessageListenerConcurrently() {
        return messageListenerConcurrently;
    }

    public void setMessageListenerConcurrently(MessageListenerConcurrently messageListenerConcurrently) {
        this.messageListenerConcurrently = messageListenerConcurrently;
    }

    public DefaultMQPushConsumer getConsumer() {
        return consumer;
    }

    public void setConsumer(DefaultMQPushConsumer consumer) {
        this.consumer = consumer;
    }
}

