package com.mytest.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class Producer {
    // 指定namesrv地址
    private static String NAMESRV_ADDRESS = "106.14.138.168:9876";

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        // 创建一个DefaultMQProducer,需要指定消息发送组。
        DefaultMQProducer  producer =  new DefaultMQProducer("Test_Producers");

        // 指定Nameser_address
        producer.setNamesrvAddr(NAMESRV_ADDRESS);

        // 启动生产者
        producer.start();

        // 创建消息
        Message message = new Message(
                "TESTa",   // 主题
                "TagB",         // 标签,可以用来做过滤
                "key",          // 唯一标识,可以用来查找消息
                "hello rocketmq".getBytes()  // 要发送的消息自己数组
        );

        //  发送消息
        SendResult result  = producer.send(message);
        // 关闭producer
        producer.shutdown();
    }
}
