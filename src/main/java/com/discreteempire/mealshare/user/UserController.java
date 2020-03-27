package com.discreteempire.mealshare.user;

import com.discreteempire.mealshare.user.api.command.CreateUser;
import com.discreteempire.mealshare.user.api.query.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CreateUserValidator createUserValidator;

    @PostMapping("/api/users")
    public User createUser(@RequestBody @Valid CreateUser createUser) {
        createUserValidator.validate(createUser);

        UserEntry user = UserEntry.create(
                createUser.getUsername(),
                createUser.getPassword(),
                passwordEncoder
        );
        userRepository.save(user);

        return user.toUser();
    }
}
