package com.healthapp.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when authentication or authorization fails.
 */
public class UnauthorizedException extends BusinessException {
    
    public UnauthorizedException(String message) {
        super("UNAUTHORIZED", message, HttpStatus.UNAUTHORIZED);
    }
    
    public static UnauthorizedException invalidCredentials() {
        return new UnauthorizedException("Invalid email/phone or password");
    }
    
    public static UnauthorizedException invalidToken() {
        return new UnauthorizedException("Invalid or expired token");
    }
    
    public static UnauthorizedException accountNotVerified() {
        return new UnauthorizedException("Account not verified. Please verify your email/phone first.");
    }
    
    public static UnauthorizedException accountSuspended() {
        return new UnauthorizedException("Your account has been suspended. Please contact support.");
    }
}
