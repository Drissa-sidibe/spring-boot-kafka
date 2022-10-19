package com.saraya.Basedomains.DTOs;

import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String orderId;
    private String name;
    private int qty;
    private double price;
    @Enumerated(EnumType.STRING)
    private Status status;

    }
