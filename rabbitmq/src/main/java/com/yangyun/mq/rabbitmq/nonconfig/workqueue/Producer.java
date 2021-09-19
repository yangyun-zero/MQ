package com.yangyun.mq.rabbitmq.nonconfig.workqueue;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yangyun.mq.rabbitmq.utils.ConnectionUtil;
import com.yangyun.mq.rabbitmq.utils.RabbitMqConstant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述: 生产者，该模式下消费多个消费消费消息的方式是轮询的方式
 * @Return:
 * @Author: 手握日月摘星辰，世间无我这般人 - yun.Yang
 * @Date: 2021/9/13 0:24
 */
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {

        // 获取连接
        Connection connection = ConnectionUtil.getConnectionFactory();
        // 获取通道，用于server链接queue
        Channel channel = connection.createChannel();
        // 申明队列
        channel.queueDeclare(RabbitMqConstant.WORK_QUEUE, false, false, false, null);

        // 模式发送短讯，循环发送100次
        Sms sms = null;
        String body = null;
        for (int i = 1; i <= 100; i++) {
            sms = new Sms();
            sms.setName("用户，");
            sms.setMobile("13400000" + i);
            sms.setContent("，您好，您预订从生产者 MQ 到消费者的飞机票出票成功！");
            body = new Gson().toJson(sms);
            // 发送消息
            channel.basicPublish("", RabbitMqConstant.WORK_QUEUE, null, body.getBytes());
        }

        channel.close();
        connection.close();
    }
}
