package com.wefox.delivery.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentConsumer {

    @KafkaListener(topics = "online", groupId = "group_id")
    public void consumeOnlinePayment(String message) {
        System.out.println("online message = " + message);
    }

    /*@KafkaListener(topics = "offline", groupId = "group_id")
    public void consumeOfflinePayment(String message) {
        System.out.println("offline message = " + message);
    }*/
}
