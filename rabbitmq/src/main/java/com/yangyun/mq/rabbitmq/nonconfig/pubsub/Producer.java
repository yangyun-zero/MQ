package com.yangyun.mq.rabbitmq.nonconfig.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yangyun.mq.rabbitmq.utils.ConnectionUtil;
import com.yangyun.mq.rabbitmq.utils.RabbitMqConstant;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述: 发布订阅模式
 *  生产者
 * @Return:
 * @Author: 手握日月摘星辰，世间无我这般人 - yun.Yang
 * @Date: 2021/9/17 20:12
 */
public class Producer {

    static Map<String, String> map = new HashMap<String, String>(){
        {
            put("aaaaa","aaaaa");
            put("bbbbb","bbbbb");
            put("ccccc","ccccc");
            put("ddddd","ddddd");
            put("eeeee","eeeee");
        }
    };

    public static void main(String[] args) throws IOException, TimeoutException {

        // 获取连接， 建立和rabbitmq server 的连接通讯
        Connection connectionFactory = ConnectionUtil.getConnectionFactory();

        // 建立通道，用于发送数据
        Channel channel = connectionFactory.createChannel();

        // 申明队列，
        channel.queueDeclare(RabbitMqConstant.PUB_SUB_QUEUE, false, false, false, null);

        // 发布消息，并申明交换机，在发布订阅模式下需要申明绑定的交换机, 并且需要手动在管理控制台创建一个交换机
        // 需要指定队列，在消费者方会指定绑定交换机
        channel.basicPublish(RabbitMqConstant.PUB_SUB_EXCHANGE, "", null, "afadba".getBytes());
        // 关闭连接
        channel.close();
        connectionFactory.close();
    }
}
