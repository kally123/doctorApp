package com.healthapp.user.model.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * User entity representing a registered user in the system.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class User {
    
    @Id
    private UUID id;
    
    private String email;
    
    private String phone;
    
    @Column("password_hash")
    private String passwordHash;
    
    @Builder.Default
    private UserRole role = UserRole.PATIENT;
    
    @Builder.Default
    private UserStatus status = UserStatus.PENDING_VERIFICATION;
    
    @Column("first_name")
    private String firstName;
    
    @Column("last_name")
    private String lastName;
    
    @Column("display_name")
    private String displayName;
    
    @Column("avatar_url")
    private String avatarUrl;
    
    @Column("date_of_birth")
    private LocalDate dateOfBirth;
    
    private String gender;
    
    @Column("email_verified")
    @Builder.Default
    private Boolean emailVerified = false;
    
    @Column("phone_verified")
    @Builder.Default
    private Boolean phoneVerified = false;
    
    @Column("google_id")
    private String googleId;
    
    @Column("apple_id")
    private String appleId;
    
    @CreatedDate
    @Column("created_at")
    private Instant createdAt;
    
    @LastModifiedDate
    @Column("updated_at")
    private Instant updatedAt;
    
    @Column("last_login_at")
    private Instant lastLoginAt;
    
    /**
     * Gets the full name of the user.
     */
    public String getFullName() {
        if (displayName != null && !displayName.isBlank()) {
            return displayName;
        }
        if (firstName != null && lastName != null) {
            return firstName + " " + lastName;
        }
        if (firstName != null) {
            return firstName;
        }
        return email != null ? email.split("@")[0] : "User";
    }
    
    /**
     * Checks if the user account is active.
     */
    public boolean isActive() {
        return status == UserStatus.ACTIVE;
    }
    
    /**
     * Checks if the user is verified.
     */
    public boolean isVerified() {
        return Boolean.TRUE.equals(emailVerified) || Boolean.TRUE.equals(phoneVerified);
    }
}
