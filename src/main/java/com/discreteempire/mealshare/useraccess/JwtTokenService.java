package com.discreteempire.mealshare.useraccess;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
class JwtTokenService {
    private final String secretKey;
    private final long validity;
    private final UserDetailsService userDetailsService;

    public String createToken(String username, List<String> roles) {
        var claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        var now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();
        var validity = now.plusSeconds(this.validity);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(validity))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Optional<Authentication> getAuthentication(HttpServletRequest request) {
        return resolveToken(request)
                .filter(this::validateToken)
                .map(this::getUsername)
                .map(userDetailsService::loadUserByUsername)
                .map(this::createAuthentication);
    }

    private Optional<String> resolveToken(HttpServletRequest req) {
        return Optional.ofNullable(req.getHeader(UserAccessConfig.TOKEN_HEADER))
                .filter(bearerToken -> bearerToken.startsWith(UserAccessConfig.TOKEN_PREFIX))
                .map(bearerToken -> bearerToken.substring(UserAccessConfig.TOKEN_PREFIX.length()));
    }

    private boolean validateToken(String token) {
        try {
            return !Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    private Authentication createAuthentication(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
