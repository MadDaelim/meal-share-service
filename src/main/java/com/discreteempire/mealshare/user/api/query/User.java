package com.discreteempire.mealshare.user.api.query;

import lombok.Value;

import java.util.UUID;

@Value
public class User {
    UUID userId;
    String username;
}
