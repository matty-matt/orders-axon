package com.kociszewski.orders.command;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class ShipOrderCommand {

    @TargetAggregateIdentifier
    private final String orderId;
}
