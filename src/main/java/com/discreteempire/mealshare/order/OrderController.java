package com.discreteempire.mealshare.order;

import com.discreteempire.mealshare.order.api.command.CreateOrder;
import com.discreteempire.mealshare.order.api.command.JoinToOrder;
import com.discreteempire.mealshare.order.api.query.Order;
import com.discreteempire.mealshare.user.UserQuery;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class OrderController {
    private final UserQuery userQuery;
    private final OrderRepository repository;

    @PostMapping("/api/orders")
    public Order createOrder(@RequestBody CreateOrder createOrder) {
        var user = userQuery.getLoggedUser();

        OrderEntry order = OrderEntry.create(
                user.getUserId(),
                createOrder.getRestaurantName(),
                createOrder.getRestaurantMenu(),
                createOrder.getDeliveryCost(),
                createOrder.getContainerCost(),
                createOrder.getOrderDate(),
                createOrder.getPickUpPlace()
        );

        repository.save(order);

        return order.toOrder();
    }

    @GetMapping("/api/orders")
    public List<Order> getOrders() {
        return repository.findAll()
                .stream()
                .map(OrderEntry::toOrder)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/orders/{orderId}")
    public Optional<Order> getOrder(@PathVariable UUID orderId) {
        return repository.findById(orderId)
                .map(OrderEntry::toOrder);
    }

    @PostMapping("/api/orders/{orderId}")
    public Order joinToOrder(@PathVariable UUID orderId, @RequestBody JoinToOrder joinToOrder) {
        var user = userQuery.getLoggedUser();

        var order = repository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.join(
                user.getUserId(),
                joinToOrder.getDishName(),
                joinToOrder.getDishPrice()
        );

        repository.save(order);

        return order.toOrder();
    }
}
