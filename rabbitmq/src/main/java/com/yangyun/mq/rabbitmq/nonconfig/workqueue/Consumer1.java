package com.yangyun.mq.rabbitmq.nonconfig.workqueue;

import com.rabbitmq.client.*;
import com.yangyun.mq.rabbitmq.utils.ConnectionUtil;
import com.yangyun.mq.rabbitmq.utils.RabbitMqConstant;

import java.io.IOException;

/**
 * 功能描述: 消费者
 * @Return:
 * @Author: 手握日月摘星辰，世间无我这般人 - yun.Yang
 * @Date: 2021/9/17 13:57
 */
public class Consumer1 {

    public static void main(String[] args) throws IOException {

        // 获取连接
        Connection connectionFactory = ConnectionUtil.getConnectionFactory();

        // 获取通道
        final Channel channel = connectionFactory.createChannel();

        // 申明队列
        channel.queueDeclare(RabbitMqConstant.WORK_QUEUE, false, false, false, null);

        // 只有当该消费者完全消费完一个消息后才会接收新的消息
        channel.basicQos(1);

        // 消费消息
        channel.basicConsume(RabbitMqConstant.WORK_QUEUE, false, new DefaultConsumer(channel){
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
