package com.discreteempire.mealshare.useraccess;

import com.discreteempire.mealshare.useraccess.api.Authenticate;
import com.discreteempire.mealshare.useraccess.api.Authenticated;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AllArgsConstructor
public class AuthenticationMother {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    public Authenticated authenticateUser(String username, String password) {
        try {
            var request = post("/api/authenticate")
                    .content(objectMapper.writeValueAsString(new Authenticate(username, password)))
                    .contentType("application/json");

            var result = mockMvc.perform(request)
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            return objectMapper.readValue(result, Authenticated.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
