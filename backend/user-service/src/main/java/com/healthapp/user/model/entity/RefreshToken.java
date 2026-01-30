package com.healthapp.user.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

/**
 * Refresh token entity for managing user sessions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("refresh_tokens")
public class RefreshToken {
    
    @Id
    private UUID id;
    
    @Column("user_id")
    private UUID userId;
    
    @Column("token_hash")
    private String tokenHash;
    
    @Column("expires_at")
    private Instant expiresAt;
    
    @Column("created_at")
    private Instant createdAt;
    
    @Column("revoked_at")
    private Instant revokedAt;
    
    @Column("device_info")
    private String deviceInfo;
    
    /**
     * Checks if the token is expired.
     */
    public boolean isExpired() {
        return expiresAt != null && expiresAt.isBefore(Instant.now());
    }
    
    /**
     * Checks if the token is revoked.
     */
    public boolean isRevoked() {
        return revokedAt != null;
    }
    
    /**
     * Checks if the token is valid (not expired and not revoked).
     */
    public boolean isValid() {
        return !isExpired() && !isRevoked();
    }
}
