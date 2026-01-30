package com.healthapp.common.exception;

import com.healthapp.common.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global exception handler for all reactive WebFlux controllers.
 * Provides consistent error responses across all services.
 */
@Slf4j
@RestControllerAdvice
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class GlobalExceptionHandler {
    
    /**
     * Handles all business exceptions.
     */
    @ExceptionHandler(BusinessException.class)
    public Mono<ResponseEntity<ApiResponse<Void>>> handleBusinessException(BusinessException ex) {
        log.warn("Business exception: {} - {}", ex.getErrorCode(), ex.getMessage());
        
        ApiResponse<Void> response = ApiResponse.error(
                ApiResponse.ErrorDetails.builder()
                        .code(ex.getErrorCode())
                        .message(ex.getMessage())
                        .details(ex.getDetails())
                        .build()
        );
        
        return Mono.just(ResponseEntity
                .status(ex.getHttpStatus())
                .body(response));
    }
    
    /**
     * Handles validation errors from @Valid annotations.
     */
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ApiResponse<Void>>> handleValidationException(WebExchangeBindException ex) {
        log.warn("Validation exception: {}", ex.getMessage());
        
        Map<String, List<String>> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));
        
        ApiResponse<Void> response = ApiResponse.error(
                ApiResponse.ErrorDetails.builder()
                        .code("VALIDATION_ERROR")
                        .message("Validation failed")
                        .details(fieldErrors)
                        .build()
        );
        
        return Mono.just(ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response));
    }
    
    /**
     * Handles invalid input errors.
     */
    @ExceptionHandler(ServerWebInputException.class)
    public Mono<ResponseEntity<ApiResponse<Void>>> handleInputException(ServerWebInputException ex) {
        log.warn("Input exception: {}", ex.getMessage());
        
        ApiResponse<Void> response = ApiResponse.error(
                ApiResponse.ErrorDetails.builder()
                        .code("INVALID_INPUT")
                        .message("Invalid request input")
                        .build()
        );
        
        return Mono.just(ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response));
    }
    
    /**
     * Handles all unexpected exceptions.
     */
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ApiResponse<Void>>> handleGenericException(Exception ex) {
        log.error("Unexpected exception: ", ex);
        
        ApiResponse<Void> response = ApiResponse.error(
                ApiResponse.ErrorDetails.builder()
                        .code("INTERNAL_ERROR")
                        .message("An unexpected error occurred. Please try again later.")
                        .build()
        );
        
        return Mono.just(ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response));
    }
}
