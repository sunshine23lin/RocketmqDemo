package com.mytest.rocketmq.ordermq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class OrderConsumer {
     private static String namesrvaddress = "106.14.138.168:9876";

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("order_consumer_group_name");
        consumer.setNamesrvAddr(namesrvaddress);
        // 设置消费顺序
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 设置消息拉取最大数
        consumer.setConsumeMessageBatchMaxSize(5);

        //设置消费主题
        consumer.subscribe("Topic_Order_Demo","TagOrder");
        // 创建消息监听
        consumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                try {
                    // 获取第一个消息
                    MessageExt message = msgs.get(1);
                    // 获取主题
                    String topic = message.getTopic();
                    // 获取标签
                    String tag = message.getTags();
                    // 获取消息
                    String result = new String(message.getBody(),"UTF-8");
                    System.out.println("topic:"+topic+",tags"+tag+",result:"+result);
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

                // 消息消费成功,返回消息消费结果
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
    }
}
