package com.discreteempire.mealshare.user;

import com.discreteempire.mealshare.IntegrationTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest extends IntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUserTest() {
        var userMother = new UserMother(mockMvc, objectMapper);

        var username = "test";
        var password = "test";

        var user = userMother.createUser(username, password);

        assertThat(user.getUsername()).isEqualTo(username);
    }
}
