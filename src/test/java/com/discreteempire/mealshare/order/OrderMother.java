package com.discreteempire.mealshare.order;

import com.discreteempire.mealshare.order.api.command.CreateOrder;
import com.discreteempire.mealshare.order.api.command.JoinToOrder;
import com.discreteempire.mealshare.order.api.query.Order;
import com.discreteempire.mealshare.useraccess.UserAccessConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AllArgsConstructor
public class OrderMother {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public OrderBuilder orderBuilder() {
        return new OrderBuilder();
    }

    public Order joinToOrder(String token, UUID orderId, String dishName, BigDecimal dishPrice) {
        try {
            var request = post("/api/orders/{orderId}", orderId)
                    .content(objectMapper.writeValueAsString(new JoinToOrder(
                            dishName,
                            dishPrice
                    ))).contentType("application/json")
                    .header(UserAccessConfig.TOKEN_HEADER, UserAccessConfig.TOKEN_PREFIX + token);

            var result = mockMvc.perform(request)
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            return objectMapper.readValue(result, Order.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public class OrderBuilder {
        private String token;
        private String restaurantName;
        private String restaurantMenu;
        private BigDecimal deliveryCost;
        private BigDecimal containerCost;
        private LocalDateTime orderDate;
        private String pickUpPlace;

        public OrderBuilder token(String token) {
            this.token = token;
            return this;
        }

        public OrderBuilder restaurantName(String restaurantName) {
            this.restaurantName = restaurantName;
            return this;
        }

        public OrderBuilder restaurantMenu(String restaurantMenu) {
            this.restaurantMenu = restaurantMenu;
            return this;
        }

        public OrderBuilder deliveryCost(BigDecimal deliveryCost) {
            this.deliveryCost = deliveryCost;
            return this;
        }

        public OrderBuilder containerCost(BigDecimal containerCost) {
            this.containerCost = containerCost;
            return this;
        }

        public OrderBuilder orderDate(LocalDateTime orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public OrderBuilder pickUpPlace(String pickUpPlace) {
            this.pickUpPlace = pickUpPlace;
            return this;
        }

        public Order build() {
            try {
                var request = post("/api/orders")
                        .content(objectMapper.writeValueAsString(new CreateOrder(
                                restaurantName,
                                restaurantMenu,
                                deliveryCost,
                                containerCost,
                                orderDate,
                                pickUpPlace
                        ))).contentType("application/json")
                        .header(UserAccessConfig.TOKEN_HEADER, UserAccessConfig.TOKEN_PREFIX + token);

                var result = mockMvc.perform(request)
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

                return objectMapper.readValue(result, Order.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
