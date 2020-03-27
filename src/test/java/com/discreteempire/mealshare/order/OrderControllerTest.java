package com.discreteempire.mealshare.order;

import com.discreteempire.mealshare.IntegrationTest;
import com.discreteempire.mealshare.order.api.query.Dish;
import com.discreteempire.mealshare.order.api.query.Order;
import com.discreteempire.mealshare.user.UserMother;
import com.discreteempire.mealshare.useraccess.AuthenticationMother;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OrderControllerTest extends IntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;

    String username = "test";
    String password = "test";

    String restaurantMenu = "http://www.ilove-sushi.pl/menu/";
    String restaurantName = "Best restaurant on the planet";
    BigDecimal deliveryCost = new BigDecimal("10.0");
    BigDecimal containerCost = new BigDecimal("1.0");
    LocalDateTime orderDate = LocalDateTime.now().plusHours(2);
    String pickUpPlace = "Pokój 4.25";

    @Test
    void createOrderTest() {
        var userMother = new UserMother(mockMvc, objectMapper);
        var authenticationMother = new AuthenticationMother(mockMvc, objectMapper);
        var orderMother = new OrderMother(mockMvc, objectMapper);

        UUID userId = userMother.createUser(username, password).getUserId();
        String token = authenticationMother.authenticateUser(username, password).getToken();

        Order order = orderMother.orderBuilder()
                .token(token)
                .restaurantName(restaurantName)
                .restaurantMenu(restaurantMenu)
                .deliveryCost(deliveryCost)
                .containerCost(containerCost)
                .orderDate(orderDate)
                .pickUpPlace(pickUpPlace)
                .build();

        assertThat(order).isNotNull();
        assertThat(order.getOwnerId()).isEqualTo(userId);
        assertThat(order.getRestaurantName()).isEqualTo(restaurantName);
        assertThat(order.getRestaurantMenu()).isEqualTo(restaurantMenu);
        assertThat(order.getDeliveryCost()).isEqualTo(deliveryCost);
        assertThat(order.getContainerCost()).isEqualTo(containerCost);
        assertThat(orderDate).isEqualTo(orderDate);
    }

    @Test
    void joinToOrderTest() {
        var userMother = new UserMother(mockMvc, objectMapper);
        var authenticationMother = new AuthenticationMother(mockMvc, objectMapper);
        var orderMother = new OrderMother(mockMvc, objectMapper);

        var userId = userMother.createUser(username, password).getUserId();
        var token = authenticationMother.authenticateUser(username, password).getToken();

        var order = orderMother.orderBuilder()
                .token(token)
                .restaurantName(restaurantName)
                .restaurantMenu(restaurantMenu)
                .deliveryCost(deliveryCost)
                .containerCost(containerCost)
                .orderDate(orderDate)
                .pickUpPlace(pickUpPlace)
                .build();

        var dishName = "Ramen";
        var dishPrice = new BigDecimal("10.0");

        var result = orderMother.joinToOrder(token, order.getOrderId(), dishName, dishPrice);

        assertThat(result.getDishes()).isNotEmpty();
        assertThat(result.getDishes()).first().isEqualTo(new Dish(userId, dishName, dishPrice));
    }
}
