package com.healthapp.user.controller;

import com.healthapp.common.dto.ApiResponse;
import com.healthapp.user.model.dto.*;
import com.healthapp.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * REST controller for user authentication operations.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "User registration and authentication endpoints")
public class AuthController {
    
    private final UserService userService;
    
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new user", description = "Creates a new user account and sends OTP for verification")
    public Mono<ApiResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request) {
        log.info("Registration request for email: {}", request.getEmail());
        return userService.register(request)
                .map(ApiResponse::success);
    }
    
    @PostMapping("/verify-otp")
    @Operation(summary = "Verify OTP", description = "Verifies the OTP sent during registration")
    public Mono<ApiResponse<LoginResponse>> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
        log.info("OTP verification for user: {}", request.getUserId());
        return userService.verifyOtp(request)
                .map(ApiResponse::success);
    }
    
    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticates user with email/phone and password")
    public Mono<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        log.info("Login request for: {}", request.getIdentifier());
        return userService.login(request)
                .map(ApiResponse::success);
    }
    
    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh token", description = "Refreshes the access token using a valid refresh token")
    public Mono<ApiResponse<TokenPair>> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return userService.refreshToken(request)
                .map(ApiResponse::success);
    }
    
    @PostMapping("/forgot-password")
    @Operation(summary = "Forgot password", description = "Initiates password reset by sending OTP")
    public Mono<ApiResponse<String>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        log.info("Forgot password request for: {}", request.getIdentifier());
        return userService.forgotPassword(request)
                .thenReturn(ApiResponse.success("OTP sent successfully"));
    }
    
    @PostMapping("/reset-password")
    @Operation(summary = "Reset password", description = "Resets password using OTP verification")
    public Mono<ApiResponse<String>> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        log.info("Reset password request for: {}", request.getIdentifier());
        return userService.resetPassword(request)
                .thenReturn(ApiResponse.success("Password reset successfully"));
    }
    
    @PostMapping("/logout")
    @Operation(summary = "Logout", description = "Revokes the refresh token")
    public Mono<ApiResponse<String>> logout(@RequestBody RefreshTokenRequest request) {
        return userService.logout(request.getRefreshToken())
                .thenReturn(ApiResponse.success("Logged out successfully"));
    }
}
