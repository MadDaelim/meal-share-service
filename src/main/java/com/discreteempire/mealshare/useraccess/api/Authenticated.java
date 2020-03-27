package com.discreteempire.mealshare.useraccess.api;

import lombok.Value;

@Value
public class Authenticated {
    String username;
    String token;
}
