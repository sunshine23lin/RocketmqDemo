package com.mytest.rocketmq.Broadcastingmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class BroadcastingConsumerDemo1 {

      private static String namesrvaddress = "106.14.138.168:9876";

    public static void main(String[] args) throws MQClientException {
        //  创建DefaultMQConsumer
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("broadcasting_consumer_group");
        //  指定namesever地址
        consumer.setNamesrvAddr(namesrvaddress);

        // 指定要消费的消息主体
        consumer.subscribe("Topic_broadcasting","*");

        // 指定消费顺序
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        // 指定一次拉取条数
        consumer.setConsumeMessageBatchMaxSize(2);

        // 指定消费模式 集群模式/广播模式
        consumer.setMessageModel(MessageModel.BROADCASTING);

        // 创建监听,监听消息
        consumer.setMessageListener(new MessageListenerConcurrently() {

            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> mags, ConsumeConcurrentlyContext context) {
                for (MessageExt msg :mags){
                    String topic = msg.getTopic();
                    String tags = msg.getTags();
                    String keys = msg.getKeys();
                    String body = null;
                    try {
                        body = new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    System.out.println("demo1        topic:"+topic+",tags:"+tags+",keys:"+keys+",body:"+body);
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动
        consumer.start();
    }
}
