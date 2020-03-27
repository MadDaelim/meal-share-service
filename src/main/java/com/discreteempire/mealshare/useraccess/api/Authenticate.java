package com.discreteempire.mealshare.useraccess.api;

import lombok.Value;

@Value
public class Authenticate {
    String username;
    String password;
}
