package com.discreteempire.mealshare.order.api.query;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Value
public class Order {
    UUID orderId;
    UUID ownerId;
    String restaurantName;
    String restaurantMenu;
    BigDecimal deliveryCost;
    BigDecimal containerCost;
    LocalDateTime orderDate;
    String pickUpPlace;
    List<Dish> dishes;
}
