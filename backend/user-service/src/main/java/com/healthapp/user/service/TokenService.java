package com.healthapp.user.service;

import com.healthapp.common.security.JwtUtils;
import com.healthapp.user.model.dto.TokenPair;
import com.healthapp.user.model.entity.RefreshToken;
import com.healthapp.user.model.entity.User;
import com.healthapp.user.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

/**
 * Service for JWT token generation and management.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
    
    private final JwtUtils jwtUtils;
    private final RefreshTokenRepository refreshTokenRepository;
    
    /**
     * Generates a new token pair (access + refresh) for the user.
     */
    public Mono<TokenPair> generateTokens(User user) {
        String accessToken = jwtUtils.generateAccessToken(
                user.getId().toString(),
                user.getEmail() != null ? user.getEmail() : user.getPhone(),
                user.getRole().name()
        );
        
        String refreshToken = UUID.randomUUID().toString();
        String tokenHash = hashToken(refreshToken);
        
        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .userId(user.getId())
                .tokenHash(tokenHash)
                .expiresAt(Instant.now().plusSeconds(jwtUtils.getRefreshTokenExpiry()))
                .createdAt(Instant.now())
                .build();
        
        return refreshTokenRepository.save(refreshTokenEntity)
                .map(saved -> TokenPair.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .accessTokenExpiresIn(jwtUtils.getAccessTokenExpiry())
                        .refreshTokenExpiresIn(jwtUtils.getRefreshTokenExpiry())
                        .build());
    }
    
    /**
     * Refreshes the access token using a valid refresh token.
     */
    public Mono<TokenPair> refreshTokens(String refreshToken, User user) {
        String tokenHash = hashToken(refreshToken);
        
        return refreshTokenRepository.findByTokenHash(tokenHash)
                .filter(token -> token.isValid() && token.getUserId().equals(user.getId()))
                .flatMap(token -> {
                    // Revoke the old refresh token (rotation)
                    return refreshTokenRepository.revokeByTokenHash(tokenHash, Instant.now())
                            .then(generateTokens(user));
                });
    }
    
    /**
     * Validates a refresh token and returns the associated user ID.
     */
    public Mono<UUID> validateRefreshToken(String refreshToken) {
        String tokenHash = hashToken(refreshToken);
        
        return refreshTokenRepository.findByTokenHash(tokenHash)
                .filter(RefreshToken::isValid)
                .map(RefreshToken::getUserId);
    }
    
    /**
     * Revokes a specific refresh token.
     */
    public Mono<Void> revokeToken(String refreshToken) {
        String tokenHash = hashToken(refreshToken);
        return refreshTokenRepository.revokeByTokenHash(tokenHash, Instant.now());
    }
    
    /**
     * Revokes all refresh tokens for a user.
     */
    public Mono<Void> revokeAllUserTokens(UUID userId) {
        return refreshTokenRepository.revokeAllByUserId(userId, Instant.now());
    }
    
    /**
     * Hashes a token using SHA-256.
     */
    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
}
