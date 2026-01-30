package com.healthapp.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a requested resource is not found.
 */
public class NotFoundException extends BusinessException {
    
    public NotFoundException(String message) {
        super("NOT_FOUND", message, HttpStatus.NOT_FOUND);
    }
    
    public NotFoundException(String resourceType, String resourceId) {
        super("NOT_FOUND", String.format("%s with id '%s' not found", resourceType, resourceId), HttpStatus.NOT_FOUND);
    }
}
