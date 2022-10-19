package com.saraya.OrderService.kafka;

import com.saraya.Basedomains.DTOs.Order;
import com.saraya.Basedomains.DTOs.OrderEvent;
import com.saraya.Basedomains.DTOs.Status;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

;
@Service
public class OrderProducer {
   // @Autowired
//    private Order orderStatus;
    Logger logger = LoggerFactory.getLogger(OrderProducer.class);
    private NewTopic topic;
//    Base-domains is added as a dependency in pom.xml to
//    get access to OrderEvent defined in the domain in this KafkaTemplate
    private KafkaTemplate<String, OrderEvent> template;

        public OrderProducer(NewTopic topic, KafkaTemplate<String, OrderEvent> template) {
        this.topic = topic;
        this.template = template;
    }
    public void notifyOrderState(OrderEvent orderEvent){
            Order order = new Order();
            order.setStatus(Status.DELIVERED);
        // logger.info(String.format("This is to notify that order has been placed", orderEvent)); Error
        if(order.getStatus()==Status.DELIVERED) {
            logger.info(String.format("OrderEvent Notification: Your order is already delivered", orderEvent));
        }else if(order.getStatus()==Status.ORDERED){
            logger.info(String.format("OrderEvent Notification: You have successfully placed an order", orderEvent));
        }else if(order.getStatus()==Status.READY){
            logger.info(String.format("OrderEvent Notification: Your order is ready for delivery", orderEvent));
        }
      else {
       logger.info(String.format("You have not existing order yet. Please make your order"));
       }

        //template.send("topic",orderEvent); This is an error because were dealing with Json.
        //you need to use kafka Message interface to create a message using messageBuilder.
        Message<OrderEvent> message = MessageBuilder.
                withPayload(orderEvent)
                .setHeader(KafkaHeaders.TOPIC, topic.name()).build();
        template.send(message);
    }
}
