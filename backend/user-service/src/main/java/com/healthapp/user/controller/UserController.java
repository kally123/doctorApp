package com.healthapp.user.controller;

import com.healthapp.common.dto.ApiResponse;
import com.healthapp.user.model.dto.UpdateProfileRequest;
import com.healthapp.user.model.dto.UserDto;
import com.healthapp.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * REST controller for user profile operations.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Profile", description = "User profile management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Returns the profile of the authenticated user")
    public Mono<ApiResponse<UserDto>> getCurrentUser(@RequestHeader("X-User-Id") String userId) {
        log.debug("Getting profile for user: {}", userId);
        return userService.getCurrentUser(UUID.fromString(userId))
                .map(ApiResponse::success);
    }
    
    @PutMapping("/me")
    @Operation(summary = "Update profile", description = "Updates the profile of the authenticated user")
    public Mono<ApiResponse<UserDto>> updateProfile(
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody UpdateProfileRequest request) {
        log.info("Updating profile for user: {}", userId);
        return userService.updateProfile(UUID.fromString(userId), request)
                .map(ApiResponse::success);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Returns a user by their ID (admin only)")
    public Mono<ApiResponse<UserDto>> getUserById(@PathVariable String id) {
        log.debug("Getting user: {}", id);
        return userService.getCurrentUser(UUID.fromString(id))
                .map(ApiResponse::success);
    }
}
