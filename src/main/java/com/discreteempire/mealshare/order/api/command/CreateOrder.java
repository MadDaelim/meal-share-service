package com.discreteempire.mealshare.order.api.command;

import lombok.Value;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class CreateOrder {
    @NotNull
    @NotEmpty
    String restaurantName;
    @NotNull
    String restaurantMenu;
    @NotNull
    @Min(value = 0)
    BigDecimal deliveryCost;
    @NotNull
    @Min(value = 0)
    BigDecimal containerCost;
    @NotNull
    @Future
    LocalDateTime orderDate;
    @NotNull
    String pickUpPlace;
}
