package com.healthapp.user.model.dto;

import lombok.Builder;
import lombok.Value;

/**
 * Response DTO for user registration.
 */
@Value
@Builder
public class RegisterResponse {
    String userId;
    String email;
    String phone;
    boolean otpSent;
    String message;
}
