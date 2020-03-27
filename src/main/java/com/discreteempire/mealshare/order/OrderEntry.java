package com.discreteempire.mealshare.order;

import com.discreteempire.mealshare.order.api.query.Order;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Document
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class OrderEntry {

    @MongoId
    private UUID id;
    private UUID owner;
    private String restaurantName;
    private String restaurantMenu;
    private BigDecimal deliveryPrice;
    private BigDecimal containerPrice;
    private LocalDateTime orderTime;
    private String pickUpPlace;
    private List<DishEntry> dishes;

    public static OrderEntry create(UUID owner,
                                    String restaurantName,
                                    String restaurantMenu,
                                    BigDecimal deliveryCost,
                                    BigDecimal containerCost,
                                    LocalDateTime orderDate,
                                    String pickUpPlace) {
        return new OrderEntry(
                UUID.randomUUID(),
                owner,
                restaurantName,
                restaurantMenu,
                deliveryCost,
                containerCost,
                orderDate,
                pickUpPlace,
                Collections.emptyList()
        );
    }

    public Order toOrder() {
        return new Order(
                id,
                owner,
                restaurantName,
                restaurantMenu,
                deliveryPrice,
                containerPrice,
                orderTime,
                pickUpPlace,
                dishes.stream()
                        .map(DishEntry::toDish)
                        .collect(Collectors.toList())
        );
    }

    public void join(UUID userId, String dish, BigDecimal dishPrice) {
        dishes.add(new DishEntry(userId, dish, dishPrice));
    }
}
