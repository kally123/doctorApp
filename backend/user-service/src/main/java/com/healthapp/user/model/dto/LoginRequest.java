package com.healthapp.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

/**
 * Request DTO for user login.
 */
@Value
@Builder
public class LoginRequest {
    
    /**
     * Email or phone number for login.
     */
    @NotBlank(message = "Email or phone is required")
    String identifier;
    
    @NotBlank(message = "Password is required")
    String password;
    
    /**
     * Device information for tracking sessions.
     */
    String deviceInfo;
}
