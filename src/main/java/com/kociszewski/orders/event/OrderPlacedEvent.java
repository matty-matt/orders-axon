package com.kociszewski.orders.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class OrderPlacedEvent {

    private final String orderId;
    private final String product;
}
