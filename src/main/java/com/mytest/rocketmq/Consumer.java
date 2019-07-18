package com.mytest.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class Consumer {
    //指定namesrv地址
    private static String NAMESRV_ADDRESS = "106.14.138.168:9876";

    public static void main(String[] args) throws MQClientException {
        // DefaultMQPushConsumer其实并不是主动向broker主动向consumer推送消息,而是consumer想broker发出请求,保持了一种长连接,broker会每秒检测一次是否有消息
        // 如果有消息,则将消息推送给consumer。使得DefaultMQPushConsumer实现消息消费,broker会主动记录消息消费的偏移量。
        // 创建DefaultMQPushConsumer
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("Test_Consumer");

        consumer.setNamesrvAddr(NAMESRV_ADDRESS);

        // 设置读取的topic
        consumer.subscribe("TESTa","*");
        // 创建消息监听
        consumer.setMessageListener(new MessageListenerConcurrently() {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            try {
                // 获取第一个消息
                MessageExt message = msgs.get(0);
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
