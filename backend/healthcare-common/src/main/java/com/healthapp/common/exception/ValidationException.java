package com.healthapp.common.exception;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

/**
 * Exception thrown when request validation fails.
 */
public class ValidationException extends BusinessException {
    
    public ValidationException(String message) {
        super("VALIDATION_ERROR", message, HttpStatus.BAD_REQUEST);
    }
    
    public ValidationException(String field, String message) {
        super("VALIDATION_ERROR", String.format("Validation failed for field '%s': %s", field, message), HttpStatus.BAD_REQUEST);
    }
    
    public ValidationException(Map<String, List<String>> fieldErrors) {
        super("VALIDATION_ERROR", "Validation failed", HttpStatus.BAD_REQUEST, fieldErrors);
    }
}
