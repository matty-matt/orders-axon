package com.kociszewski.orders;

import com.kociszewski.orders.aggregate.Order;
import com.kociszewski.orders.command.ConfirmOrderCommand;
import com.kociszewski.orders.command.PlaceOrderCommand;
import com.kociszewski.orders.command.ShipOrderCommand;
import com.kociszewski.orders.event.OrderConfirmedEvent;
import com.kociszewski.orders.event.OrderPlacedEvent;
import com.kociszewski.orders.event.OrderShippedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class OrderTest {

    private FixtureConfiguration<Order> fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(Order.class);
    }

    @Test
    public void shouldPlaceProduct() {
        String orderId = UUID.randomUUID().toString();
        String product = "Deluxe chair";
        fixture.givenNoPriorActivity()
                .when(new PlaceOrderCommand(orderId, product))
                .expectEvents(new OrderPlacedEvent(orderId, product));
    }

    @Test
    public void shouldConfirmProduct() {
        String orderId = UUID.randomUUID().toString();
        String product = "Deluxe chair";
        fixture.given(new OrderPlacedEvent(orderId, product))
                .when(new ConfirmOrderCommand(orderId))
                .expectEvents(new OrderConfirmedEvent(orderId));
    }

    @Test
    public void shouldShipProduct() {
        String orderId = UUID.randomUUID().toString();
        String product = "Deluxe chair";
        fixture.given(new OrderPlacedEvent(orderId, product), new OrderConfirmedEvent(orderId))
                .when(new ShipOrderCommand(orderId))
                .expectEvents(new OrderShippedEvent(orderId));
    }

    @Test
    public void shouldNotShipProductWhenNotConfirmed() {
        String orderId = UUID.randomUUID().toString();
        String product = "Deluxe chair";
        fixture.given(new OrderPlacedEvent(orderId, product))
                .when(new ShipOrderCommand(orderId))
                .expectException(IllegalArgumentException.class);
    }
}
