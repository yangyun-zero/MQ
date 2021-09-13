package com.yangyun.mq.rabbitmq.nonconfig.helloworld;

import com.rabbitmq.client.*;
import com.yangyun.mq.rabbitmq.utils.ConnectionUtil;
import com.yangyun.mq.rabbitmq.utils.RabbitMqConstant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1. mq 生产者建立跟 mq server 的连接
        // 获取连接工厂
//        ConnectionFactory connectionFactory = new ConnectionFactory();
//        // 设置地址
//        connectionFactory.setHost("47.107.172.70");
//        // 设置端口
//        connectionFactory.setPort(5672);
//        connectionFactory.setUsername("yangyun01");
//        connectionFactory.setPassword("yangyun01");
//        connectionFactory.setVirtualHost("yangyun01");

        // 获取tcp长连接
        Connection connection = ConnectionUtil.getConnectionFactory();
        // 通过 chanel 建立虚拟连接，与rabbitmq server实现连接
        Channel channel = connection.createChannel();
        // 通过channel创建一个队列，如果没有创建，会使用一个默认队列
        // 1 队列名称 2 是否持久化，false 为否，true 为是，false表示 MQ 停掉会丢失数据
        // 3 是否私有化队列，false 表示所有消费者都可以访问该队列，true 表示只有第一次拥有他的消费者才能一直使用
        // 4 是否删除该队列，false 表示连接断开后不会删除
        channel.queueDeclare(RabbitMqConstant.HELLOWORD_QUEUE, false, false, false, null);

        String message = "yangyun niubi";
        // 1 交换机，因为为简单模式发布消息，mq底层会使用默认交换机
        // 2 消息发送到那个队列 3 额外信息，可以不填  4 发送的消息，为 byte[]
        channel.basicPublish("", RabbitMqConstant.HELLOWORD_QUEUE, null, message.getBytes());

        // 关闭连接
        channel.close();
        connection.close();
    }
}