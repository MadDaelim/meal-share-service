package com.discreteempire.mealshare.user;

import com.discreteempire.mealshare.user.api.query.User;
import com.discreteempire.mealshare.user.api.query.UserCredentials;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Document
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class UserEntry {

    @MongoId
    private UUID id;
    private String username;
    private String password;

    public static UserEntry create(String username,
                                   String password,
                                   PasswordEncoder passwordEncoder) {

        return new UserEntry(
                UUID.randomUUID(),
                username,
                passwordEncoder.encode(password)
        );
    }

    public UserCredentials toUserCredentials() {
        return new UserCredentials(username, password);
    }

    public User toUser() {
        return new User(id, username);
    }
}
