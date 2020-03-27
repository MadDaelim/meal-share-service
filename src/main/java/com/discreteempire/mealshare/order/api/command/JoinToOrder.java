package com.discreteempire.mealshare.order.api.command;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class JoinToOrder {
    String dishName;
    BigDecimal dishPrice;
}
