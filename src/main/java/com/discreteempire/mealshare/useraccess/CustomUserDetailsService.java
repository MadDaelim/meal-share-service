package com.discreteempire.mealshare.useraccess;

import com.discreteempire.mealshare.user.UserQuery;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@AllArgsConstructor
class CustomUserDetailsService implements UserDetailsService {
    private final UserQuery users;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.users.getUserCredentials(username)
                .map(user -> new CustomUserDetails(user.getUsername(), user.getPassword()))
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
    }
}
