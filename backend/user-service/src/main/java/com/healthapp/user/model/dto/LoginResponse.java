package com.healthapp.user.model.dto;

import lombok.Builder;
import lombok.Value;

/**
 * Response DTO for successful login containing tokens.
 */
@Value
@Builder
public class LoginResponse {
    String accessToken;
    String refreshToken;
    long accessTokenExpiresIn;
    long refreshTokenExpiresIn;
    String tokenType;
    UserDto user;
    
    public static LoginResponse of(TokenPair tokens, UserDto user) {
        return LoginResponse.builder()
                .accessToken(tokens.getAccessToken())
                .refreshToken(tokens.getRefreshToken())
                .accessTokenExpiresIn(tokens.getAccessTokenExpiresIn())
                .refreshTokenExpiresIn(tokens.getRefreshTokenExpiresIn())
                .tokenType("Bearer")
                .user(user)
                .build();
    }
}
