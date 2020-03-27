package com.discreteempire.mealshare;

import com.discreteempire.mealshare.user.UserQuery;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class HelloApi {

    private final UserQuery userQuery;

    @GetMapping("/api/hello")
    public String sayHello() {
        var loggedUser = userQuery.getLoggedUser();

        return "Hello " + loggedUser.getUsername() + "!";
    }
}
