package com.healthapp.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Base exception for all business/domain exceptions.
 */
@Getter
public class BusinessException extends RuntimeException {
    
    private final String errorCode;
    private final HttpStatus httpStatus;
    private final Object details;
    
    public BusinessException(String message) {
        super(message);
        this.errorCode = "BUSINESS_ERROR";
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.details = null;
    }
    
    public BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.details = null;
    }
    
    public BusinessException(String errorCode, String message, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.details = null;
    }
    
    public BusinessException(String errorCode, String message, HttpStatus httpStatus, Object details) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.details = details;
    }
    
    public BusinessException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.details = null;
    }
}
