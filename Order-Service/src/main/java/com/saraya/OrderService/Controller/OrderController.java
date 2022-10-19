package com.saraya.OrderService.Controller;

import com.saraya.Basedomains.DTOs.Order;
import com.saraya.Basedomains.DTOs.OrderEvent;
import com.saraya.Basedomains.DTOs.Status;
import com.saraya.OrderService.kafka.OrderProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
///api/v1/name
@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private OrderProducer orderProducer;
    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }



    @PostMapping("/orders")
    public String placeOrder(@RequestBody Order order) {
        Order or = new Order();
        or.setStatus(Status.ORDERED);
        //generate random id for orders'
        order.setOrderId(UUID.randomUUID().toString());


        OrderEvent orderEvent = new OrderEvent();
//        orderEvent.setStatus("ORDERED");
//        orderEvent.setMessage("This order" + order + "is ORDERED");
        orderEvent.setOrder(order);
        orderProducer.notifyOrderState(orderEvent);
        return "Order Placed successfully........";

    }
    @GetMapping("/{name}")
    public ResponseEntity<Order> viewOrder(@PathVariable("name") String name, Order order){
        order.setOrderId(UUID.randomUUID().toString());
        order.getName();
//        order.getPrice();
//        order.getQty();
        OrderEvent event = new OrderEvent();
        event.setOrder(order);
//        event.setStatus("Received");
//        event.setMessage("Order is received");
        orderProducer.notifyOrderState(event);
        return new ResponseEntity<>(order, HttpStatus.FOUND);
    }

}