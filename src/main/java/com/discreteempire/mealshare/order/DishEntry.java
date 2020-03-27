package com.discreteempire.mealshare.order;

import com.discreteempire.mealshare.order.api.query.Dish;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
public class DishEntry {
    UUID userId;
    String name;
    BigDecimal price;

    public Dish toDish() {
        return new Dish(
                userId,
                name,
                price
        );
    }
}
