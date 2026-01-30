package com.healthapp.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * Utility class for JWT token generation and validation.
 */
@Slf4j
@Component
public class JwtUtils {
    
    private final SecretKey secretKey;
    private final long accessTokenExpiry;
    private final long refreshTokenExpiry;
    
    public JwtUtils(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expiry:900}") long accessTokenExpiry,
            @Value("${jwt.refresh-token-expiry:604800}") long refreshTokenExpiry) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpiry = accessTokenExpiry;
        this.refreshTokenExpiry = refreshTokenExpiry;
    }
    
    /**
     * Generates an access token for the given user.
     */
    public String generateAccessToken(String userId, String email, String role) {
        return generateAccessToken(userId, Map.of(
                "email", email,
                "role", role
        ));
    }
    
    /**
     * Generates an access token with custom claims.
     */
    public String generateAccessToken(String userId, Map<String, Object> claims) {
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(accessTokenExpiry);
        
        var builder = Jwts.builder()
                .subject(userId)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry));
        
        claims.forEach(builder::claim);
        
        return builder.signWith(secretKey).compact();
    }
    
    /**
     * Generates a refresh token.
     */
    public String generateRefreshToken(String userId) {
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(refreshTokenExpiry);
        
        return Jwts.builder()
                .subject(userId)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .claim("type", "refresh")
                .signWith(secretKey)
                .compact();
    }
    
    /**
     * Validates a token and returns the claims.
     */
    public Optional<Claims> validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return Optional.of(claims);
        } catch (JwtException e) {
            log.debug("Token validation failed: {}", e.getMessage());
            return Optional.empty();
        }
    }
    
    /**
     * Extracts the user ID from a token.
     */
    public Optional<String> getUserIdFromToken(String token) {
        return validateToken(token).map(Claims::getSubject);
    }
    
    /**
     * Extracts a claim from a token.
     */
    public <T> Optional<T> getClaimFromToken(String token, String claimName, Class<T> claimType) {
        return validateToken(token).map(claims -> claims.get(claimName, claimType));
    }
    
    /**
     * Checks if a token is expired.
     */
    public boolean isTokenExpired(String token) {
        return validateToken(token)
                .map(claims -> claims.getExpiration().before(new Date()))
                .orElse(true);
    }
    
    public long getAccessTokenExpiry() {
        return accessTokenExpiry;
    }
    
    public long getRefreshTokenExpiry() {
        return refreshTokenExpiry;
    }
}
