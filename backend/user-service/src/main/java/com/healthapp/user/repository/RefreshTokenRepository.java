package com.healthapp.user.repository;

import com.healthapp.user.model.entity.RefreshToken;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

/**
 * Reactive repository for RefreshToken entity.
 */
@Repository
public interface RefreshTokenRepository extends R2dbcRepository<RefreshToken, UUID> {
    
    /**
     * Finds a refresh token by its hash.
     */
    Mono<RefreshToken> findByTokenHash(String tokenHash);
    
    /**
     * Finds all refresh tokens for a user.
     */
    Flux<RefreshToken> findByUserId(UUID userId);
    
    /**
     * Finds active (not revoked) tokens for a user.
     */
    @Query("SELECT * FROM refresh_tokens WHERE user_id = :userId AND revoked_at IS NULL AND expires_at > :now")
    Flux<RefreshToken> findActiveTokensByUserId(UUID userId, Instant now);
    
    /**
     * Revokes all tokens for a user.
     */
    @Modifying
    @Query("UPDATE refresh_tokens SET revoked_at = :revokedAt WHERE user_id = :userId AND revoked_at IS NULL")
    Mono<Void> revokeAllByUserId(UUID userId, Instant revokedAt);
    
    /**
     * Revokes a specific token by its hash.
     */
    @Modifying
    @Query("UPDATE refresh_tokens SET revoked_at = :revokedAt WHERE token_hash = :tokenHash AND revoked_at IS NULL")
    Mono<Void> revokeByTokenHash(String tokenHash, Instant revokedAt);
    
    /**
     * Deletes expired tokens (cleanup job).
     */
    @Modifying
    @Query("DELETE FROM refresh_tokens WHERE expires_at < :expiredBefore")
    Mono<Integer> deleteExpiredTokens(Instant expiredBefore);
}
