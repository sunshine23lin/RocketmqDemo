package com.mytest.rocketmq.ordermq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.List;


public class OrderProducer {
    private static String namesrvaddress = "106.14.138.168:9876";

        public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
            // 创建Default
            DefaultMQProducer producer = new DefaultMQProducer("order_producer_group_name");
           // 设置namesrv地址
            producer.setNamesrvAddr(namesrvaddress);
            // 启动Prodeucer
            producer.start();
            // 创建消息
            Message message = new Message(
                    "Topic_Order_Demo",
                    "TagOrder",
                    "KeyOrder",
                    "hello order message111111111111!".getBytes(RemotingHelper.DEFAULT_CHARSET));// 根据传入的参数选择使用的队列数据

            // 发送消息
            /**
             * args是下面传入1,指定第几个队列
             * mqs是队列的集合:它不一定是4个,取决于当前集群个数(从节点不算)设置的队列数
             */
            SendResult result = producer.send(
                    message, // 要发送的消息
                    new MessageQueueSelector() {
                        @Override
                        public MessageQueue select(List<MessageQueue> mqs, Message message, Object arg) {
                            return mqs.get((Integer) arg);
                        }
                    },
                    1   // 设置存入第几个队列中
            );
            // 关闭
            producer.shutdown();
    }
}
