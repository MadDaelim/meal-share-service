package com.discreteempire.mealshare.order.api.query;

import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
public class Dish {
    UUID userId;
    String name;
    BigDecimal price;
}
