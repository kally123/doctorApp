package com.healthapp.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

/**
 * Request DTO for refreshing access token.
 */
@Value
@Builder
public class RefreshTokenRequest {
    
    @NotBlank(message = "Refresh token is required")
    String refreshToken;
}
