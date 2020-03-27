package com.discreteempire.mealshare.user;

import com.discreteempire.mealshare.user.api.command.CreateUser;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class CreateUserValidator {

    private final UserRepository userRepository;

    public void validate(CreateUser createUser) {
        var password = createUser.getPassword();
        var retypedPassword = createUser.getRetypedPassword();

        if (!password.equals(retypedPassword)) {
            throw new RuntimeException("Password is different than retyped password.");
        }

        if(userRepository.existsByUsername(createUser.getUsername())) {
            throw new RuntimeException("User with username already exists.");
        }
    }
}
