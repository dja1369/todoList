package com.learn.todolist.infra;

import com.learn.todolist.utils.EnvUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class TokenProvider {

    private final String secret = EnvUtils.getEnv("jwt-secret-key");
    private final long accessExpirationHours = Long.parseLong(EnvUtils.getEnv("jwt-access-expiration-hours"));
    private final String issuer = EnvUtils.getEnv("jwt-issuer");

    private final byte[] key = Keys.hmacShaKeyFor(secret.getBytes()).getEncoded();

    public String generateToken(UUID userId) {
        Map<String, Object> claims = Map.of("type", "access");
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(userId))
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpirationHours * 60 * 60 * 1000))
                .signWith(Keys.hmacShaKeyFor(key))
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(key))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public UsernamePasswordAuthenticationToken authenticateToken(String token) {
        var claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(key))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, null);
    }
}