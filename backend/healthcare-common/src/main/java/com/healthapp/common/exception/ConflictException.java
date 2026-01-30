package com.healthapp.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a conflict occurs (e.g., duplicate resource).
 */
public class ConflictException extends BusinessException {
    
    public ConflictException(String message) {
        super("CONFLICT", message, HttpStatus.CONFLICT);
    }
    
    public static ConflictException duplicateEmail(String email) {
        return new ConflictException(String.format("User with email '%s' already exists", email));
    }
    
    public static ConflictException duplicatePhone(String phone) {
        return new ConflictException(String.format("User with phone '%s' already exists", phone));
    }
    
    public static ConflictException duplicateResource(String resourceType, String identifier) {
        return new ConflictException(String.format("%s with identifier '%s' already exists", resourceType, identifier));
    }
}
