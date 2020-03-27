package com.discreteempire.mealshare.user;

import com.discreteempire.mealshare.user.api.query.User;
import com.discreteempire.mealshare.user.api.query.UserCredentials;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@AllArgsConstructor
public class UserQuery {
    private final UserRepository userRepository;

    public Optional<UserCredentials> getUserCredentials(String username) {
        return userRepository.findByUsername(username)
                .map(UserEntry::toUserCredentials);
    }

    public User getLoggedUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .map(UserEntry::toUser)
                .orElseThrow(() -> new RuntimeException("User not logged"));
    }
}
