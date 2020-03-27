package com.discreteempire.mealshare.user;

import com.discreteempire.mealshare.user.api.command.CreateUser;
import com.discreteempire.mealshare.user.api.query.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AllArgsConstructor
public class UserMother {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    public User createUser(String username, String password) {
        try {
            var request = post("/api/users")
                    .content(objectMapper.writeValueAsString(new CreateUser(username, password, password)))
                    .contentType("application/json");

            var result = mockMvc.perform(request)
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            return objectMapper.readValue(result, User.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
