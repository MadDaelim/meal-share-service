package com.discreteempire.mealshare.useraccess;

import com.discreteempire.mealshare.useraccess.api.Authenticate;
import com.discreteempire.mealshare.useraccess.api.Authenticated;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    @PostMapping(UserAccessConfig.AUTH_LOGIN_URL)
    public Authenticated attemptAuthentication(@RequestBody Authenticate authenticate) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(authenticate.getUsername(), authenticate.getPassword());

        var authentication = authenticationManager.authenticate(authenticationToken);
        if (authentication != null) {
            var token = jwtTokenService.createToken(authentication.getName(), List.of("TEST"));
            return new Authenticated(authentication.getName(), token);
        }

        return null;
    }
}
