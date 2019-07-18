package com.mytest.mqandspring.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class PushConsumerHandler implements MessageListenerConcurrently{
        // 创建消息监听
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        for (MessageExt msg :msgs) {
            try {

                // 获取消息
                String msgJson = new String(msg.getBody(), "UTF-8");
                System.out.println(msgJson);
                JSONObject jsonObject = null;
                jsonObject = JSON.parseObject(msgJson);
                String agreement = jsonObject.getString("agreement");
                System.out.println(agreement);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }

        }
        // 消息消费成功,返回消息消费结果
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
