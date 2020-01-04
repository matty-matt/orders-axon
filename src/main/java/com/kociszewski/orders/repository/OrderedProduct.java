package com.kociszewski.orders.repository;

import lombok.Data;

@Data
public class OrderedProduct {
    private final String orderId;
    private final String product;
    private OrderStatus orderStatus;

    public OrderedProduct(String orderId, String product) {
        this.orderId = orderId;
        this.product = product;
        this.orderStatus = OrderStatus.PLACED;
    }
}

