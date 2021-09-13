package com.yangyun.mq.rabbitmq.nonconfig.workqueue;

import lombok.Data;

import java.io.Serializable;

@Data
public class Sms implements Serializable {

    private String name;
    private String mobile;
    private String content;
}
