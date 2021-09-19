package com.yangyun.mq.rabbitmq.nonconfig.pubsub;

import com.rabbitmq.client.*;
import com.yangyun.mq.rabbitmq.utils.ConnectionUtil;
import com.yangyun.mq.rabbitmq.utils.RabbitMqConstant;

import java.io.IOException;

public class ConsumerA {

    public static void main(String[] args) throws Exception {
        // 获取连接
        Connection connectionFactory = ConnectionUtil.getConnectionFactory();

        // 获取通道
        final Channel channel = connectionFactory.createChannel();

        // 申明队列
        channel.queueDeclare(RabbitMqConstant.PUB_SUB_QUEUE, false, false, false, null);

        // 绑定队列和交换机
        // 1 队列名称 2 队列绑定的交换机名称 3 路由key
        channel.queueBind(RabbitMqConstant.PUB_SUB_QUEUE, RabbitMqConstant.PUB_SUB_EXCHANGE, "");

        channel.basicQos(1);

        channel.basicConsume(RabbitMqConstant.PUB_SUB_QUEUE, false, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("producer: " + envelope.getDeliveryTag());
                System.out.println("consumer: " + consumerTag);
                System.out.println(new String (body));

                // 确认收到消息，没有确认，当前消费消息会挂起
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
