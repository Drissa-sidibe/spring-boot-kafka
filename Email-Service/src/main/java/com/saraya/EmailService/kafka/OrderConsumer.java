package com.saraya.EmailService.kafka;

import com.saraya.Basedomains.DTOs.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class OrderConsumer {
   Logger logger= LoggerFactory.getLogger(OrderConsumer.class);

    //"${spring.kafka.topic.name}"
    @KafkaListener(topics = "order_topic", groupId = "email")
    public void readOrderNotification(OrderEvent orderEvent) {
        logger.info(String.format("Order Event  Received in EmailService:  .....", orderEvent.toString()));
        //Implement Email Service

    }
}


