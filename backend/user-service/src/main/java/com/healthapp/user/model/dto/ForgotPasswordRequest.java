package com.healthapp.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

/**
 * Request DTO for initiating password reset.
 */
@Value
@Builder
public class ForgotPasswordRequest {
    
    @NotBlank(message = "Email or phone is required")
    String identifier;
}
