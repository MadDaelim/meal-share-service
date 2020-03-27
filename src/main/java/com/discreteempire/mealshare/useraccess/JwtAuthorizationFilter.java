package com.discreteempire.mealshare.useraccess;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtTokenService jwtTokenService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService) {
        super(authenticationManager);
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        jwtTokenService.getAuthentication(request)
                .ifPresent(value -> SecurityContextHolder.getContext().setAuthentication(value));

        filterChain.doFilter(request, response);
    }
}
