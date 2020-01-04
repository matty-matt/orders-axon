package com.kociszewski.orders.rest;

import com.kociszewski.orders.command.ConfirmOrderCommand;
import com.kociszewski.orders.command.PlaceOrderCommand;
import com.kociszewski.orders.command.ShipOrderCommand;
import com.kociszewski.orders.query.FindAllOrderedProductsQuery;
import com.kociszewski.orders.repository.OrderedProduct;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OrderRestEndpoint {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @PostMapping("ship")
    public void shipOrder(@RequestBody Product product) {
        String orderId = UUID.randomUUID().toString();
        commandGateway.send(new PlaceOrderCommand(orderId, product.getName()));
        commandGateway.send(new ConfirmOrderCommand(orderId));
        commandGateway.send(new ShipOrderCommand(orderId));
    }

    @GetMapping("orders")
    public List<OrderedProduct> getAllOrderedProducts() {
        return queryGateway.query(new FindAllOrderedProductsQuery(),
                ResponseTypes.multipleInstancesOf(OrderedProduct.class)).join();
    }
}
