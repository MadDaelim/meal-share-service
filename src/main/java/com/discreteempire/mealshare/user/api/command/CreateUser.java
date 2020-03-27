package com.discreteempire.mealshare.user.api.command;

import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class CreateUser {
    @NotNull
    String username;
    @NotNull
    String password;
    @NotNull
    String retypedPassword;
}
