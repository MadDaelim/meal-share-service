package com.discreteempire.mealshare.useraccess;

import com.discreteempire.mealshare.IntegrationTest;
import com.discreteempire.mealshare.user.UserMother;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class AuthenticationControllerTest extends IntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void authenticateUserTest() {
        var userMother = new UserMother(mockMvc, objectMapper);
        var authenticationMother = new AuthenticationMother(mockMvc, objectMapper);

        var username = "test";
        var password = "test";
        userMother.createUser(username, password);

        var authenticated = authenticationMother.authenticateUser(username, password);

        assertThat(authenticated.getToken()).isNotBlank();
    }
}
