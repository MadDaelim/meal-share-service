package com.discreteempire.mealshare.useraccess;

import com.discreteempire.mealshare.user.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class UserAccessConfig extends WebSecurityConfigurerAdapter {
    public static final String AUTH_LOGIN_URL = "/api/authenticate";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String[] GET_AUTH_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
    };

    private static final String[] POST_AUTH_WHITELIST = {
            AUTH_LOGIN_URL,
            "/api/users"
    };

    private final UserQuery userQuery;

    @Value("${jwt-secret}")
    private String secret;
    @Value("${jwt-validity-period}")
    private long validityPeriod;

    public UserAccessConfig(UserQuery userQuery) {
        this.userQuery = userQuery;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new CustomUserDetailsService(userQuery))
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    JwtTokenService jwtTokenService() {
        return new JwtTokenService(secret, validityPeriod, new CustomUserDetailsService(userQuery));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, GET_AUTH_WHITELIST).permitAll()
                .antMatchers(HttpMethod.POST, POST_AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAt(new JwtAuthorizationFilter(authenticationManager(), jwtTokenService()), UsernamePasswordAuthenticationFilter.class);
    }
}
