package com.kociszewski.orders.aggregate;

import com.kociszewski.orders.command.ConfirmOrderCommand;
import com.kociszewski.orders.command.PlaceOrderCommand;
import com.kociszewski.orders.command.ShipOrderCommand;
import com.kociszewski.orders.event.OrderConfirmedEvent;
import com.kociszewski.orders.event.OrderPlacedEvent;
import com.kociszewski.orders.event.OrderShippedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.common.Assert;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class Order {

    @AggregateIdentifier
    private String orderId;
    private boolean orderConfirmed;

    @CommandHandler
    public Order(PlaceOrderCommand command) {
        apply(new OrderPlacedEvent(command.getOrderId(), command.getProduct()));
    }

    protected Order() {
    }

    @CommandHandler
    public void handle(ConfirmOrderCommand command) {
        apply(new OrderConfirmedEvent(command.getOrderId()));
    }

    @CommandHandler
    public void handle(ShipOrderCommand command) {
        Assert.isTrue(orderConfirmed, () -> "Order is not confirmed!");
        apply(new OrderShippedEvent(command.getOrderId()));
    }

    @EventSourcingHandler
    public void on(OrderPlacedEvent event) {
        this.orderId = event.getOrderId();
        orderConfirmed = false;
    }

    @EventSourcingHandler
    public void on(OrderConfirmedEvent event) {
        orderConfirmed = true;
    }

    @EventSourcingHandler
    public void on(OrderShippedEvent event) {
    }
}