package com.healthapp.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

/**
 * Request DTO for OTP verification.
 */
@Value
@Builder
public class VerifyOtpRequest {
    
    @NotBlank(message = "User ID is required")
    String userId;
    
    @NotBlank(message = "OTP is required")
    @Size(min = 6, max = 6, message = "OTP must be 6 digits")
    String otp;
}
