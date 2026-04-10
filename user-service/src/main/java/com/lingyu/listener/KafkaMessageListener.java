package com.lingyu.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageListener {

    @KafkaListener(topics = "test", groupId = "test-group")
    public void receiveMessage(String message) {
        System.out.println("收到消息: " + message);
    }
}
