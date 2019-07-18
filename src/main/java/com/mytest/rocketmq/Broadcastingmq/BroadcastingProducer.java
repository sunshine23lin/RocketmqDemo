package com.mytest.rocketmq.Broadcastingmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class BroadcastingProducer {
    private static String namesrvaddress = "106.14.138.168:9876";

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer =  new DefaultMQProducer("broadcasting_producer_group");
        producer.setNamesrvAddr(namesrvaddress);

        producer.start();

        // 创建消息
        List<Message> messages = new ArrayList<Message>();
        for (int i=0;i<20;i++){
            Message message = new Message(
                    "Topic_broadcasting",
                    "TagBroad",
                    "KeyBroad"+i,
                    (i+"--hello brodcasting").getBytes(RemotingHelper.DEFAULT_CHARSET));
            // 将消息添加的到集合中
            messages.add(message);
        }
        // 批量发送消息
        producer.send(messages);
        // 关闭
        producer.shutdown();
    }
}
