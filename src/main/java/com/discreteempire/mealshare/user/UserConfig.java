package com.discreteempire.mealshare.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public UserQuery userFacade(UserRepository userRepository) {
        return new UserQuery(userRepository);
    }

    @Bean
    CreateUserValidator createUserValidator(UserRepository userRepository) {
        return new CreateUserValidator(userRepository);
    }
}
