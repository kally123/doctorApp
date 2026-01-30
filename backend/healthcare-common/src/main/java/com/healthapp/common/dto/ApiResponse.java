package com.healthapp.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;

/**
 * Standard API response wrapper for all endpoints.
 * Provides consistent response structure across all services.
 */
@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    
    boolean success;
    T data;
    String message;
    ErrorDetails error;
    Instant timestamp;
    String requestId;
    
    /**
     * Creates a successful response with data.
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .timestamp(Instant.now())
                .build();
    }
    
    /**
     * Creates a successful response with data and message.
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .message(message)
                .timestamp(Instant.now())
                .build();
    }
    
    /**
     * Creates an error response.
     */
    public static <T> ApiResponse<T> error(String code, String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .error(ErrorDetails.builder()
                        .code(code)
                        .message(message)
                        .build())
                .timestamp(Instant.now())
                .build();
    }
    
    /**
     * Creates an error response with details.
     */
    public static <T> ApiResponse<T> error(ErrorDetails error) {
        return ApiResponse.<T>builder()
                .success(false)
                .error(error)
                .timestamp(Instant.now())
                .build();
    }
    
    @Value
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ErrorDetails {
        String code;
        String message;
        Object details;
        String path;
    }
}
