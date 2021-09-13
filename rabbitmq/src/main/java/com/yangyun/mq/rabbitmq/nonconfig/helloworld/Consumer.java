package com.yangyun.mq.rabbitmq.nonconfig.helloworld;

import com.rabbitmq.client.*;
import com.yangyun.mq.rabbitmq.utils.ConnectionUtil;
import com.yangyun.mq.rabbitmq.utils.RabbitMqConstant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {

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

        Connection connection = ConnectionUtil.getConnectionFactory();

        Channel channel = connection.createChannel();

        channel.queueDeclare(RabbitMqConstant.HELLOWORD_QUEUE, false, false, false, null);

        // 从 mq 中拉取数据
        // 1 需要获取数据的队列   2  获取数据后的回调方法
        /**
         * 3 是否自动确认收到消息，false 表示手动编写程序来确认，mq 推荐用法
         * @see com.rabbitmq.client.Consumer
         */
        channel.basicConsume(RabbitMqConstant.HELLOWORD_QUEUE, false, new Receiver(channel));


        // 没有关闭连接，以防无法接受到消息
    }
}


/**
 * 功能描述: 消费者手动确认签名确认接受到消息
 * @Return:
 * @Author: 手握日月摘星辰，世间无我这般人 - yun.Yang
 * @Date: 2021/9/12 22:58
 */
class Receiver extends DefaultConsumer {

    private Channel channel;

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public Receiver(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    /**
     * 功能描述: 重写签名方法，确认接受到消息
     * @param consumerTag：
     * @param envelope：
     * @param properties：
     * @param body：
     * @Return: void
     * @Author: 手握日月摘星辰，世间无我这般人 - yun.Yang
     * @Date: 2021/9/12 22:54
     */
    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        // 接受的具体消息
        String s = new String(body);
        // 确认签名 Envelope 记录生产者（）的基本信息， 是否消费该消费者所有未消费的消息，false，表示只消费当前队列的为消费的消息
        channel.basicAck(envelope.getDeliveryTag(), false);
        System.out.println("消费者接受到的消息：" + s);
        System.out.println(envelope.getDeliveryTag());
        System.out.println("routingkey: " + envelope.getRoutingKey());
        System.out.println("exchange: " + envelope.getExchange());
    }
}
