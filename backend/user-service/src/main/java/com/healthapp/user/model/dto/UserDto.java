package com.healthapp.user.model.dto;

import com.healthapp.user.model.entity.UserRole;
import com.healthapp.user.model.entity.UserStatus;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.time.LocalDate;

/**
 * User DTO for external representation.
 */
@Value
@Builder
public class UserDto {
    String id;
    String email;
    String phone;
    UserRole role;
    UserStatus status;
    String firstName;
    String lastName;
    String displayName;
    String avatarUrl;
    LocalDate dateOfBirth;
    String gender;
    boolean emailVerified;
    boolean phoneVerified;
    Instant createdAt;
    Instant lastLoginAt;
}
