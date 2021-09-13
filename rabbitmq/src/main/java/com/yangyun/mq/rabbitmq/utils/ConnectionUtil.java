package com.yangyun.mq.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectionUtil {

    private static ConnectionFactory connectionFactory = new ConnectionFactory();

    static {
        // 设置地址
        connectionFactory.setHost("47.107.172.70");
        // 设置端口
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("yangyun01");
        connectionFactory.setPassword("yangyun01");
        connectionFactory.setVirtualHost("yangyun01");
    }


    public static Connection getConnectionFactory (){
        Connection connection = null;
        try {
            connection = connectionFactory.newConnection();
        } catch (Exception e){
            log.error("Rabbit MQ Connection Create Error by ", e.getMessage());
        }
        return connection;
    }
}
