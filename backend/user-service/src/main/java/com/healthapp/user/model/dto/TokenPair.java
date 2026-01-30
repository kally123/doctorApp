package com.healthapp.user.model.dto;

import lombok.Builder;
import lombok.Value;

/**
 * Token pair containing access and refresh tokens.
 */
@Value
@Builder
public class TokenPair {
    String accessToken;
    String refreshToken;
    long accessTokenExpiresIn;
    long refreshTokenExpiresIn;
    
    @Builder.Default
    String tokenType = "Bearer";
}
