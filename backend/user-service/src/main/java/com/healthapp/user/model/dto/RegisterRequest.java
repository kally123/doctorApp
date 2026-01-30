package com.healthapp.user.model.dto;

import com.healthapp.user.model.entity.UserRole;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Value;

/**
 * Request DTO for user registration.
 */
@Value
@Builder
public class RegisterRequest {
    
    @Email(message = "Invalid email address")
    String email;
    
    @Pattern(regexp = "^\\+?[1-9]\\d{9,14}$", message = "Invalid phone number")
    String phone;
    
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
        message = "Password must contain at least one uppercase letter, one lowercase letter, and one number"
    )
    String password;
    
    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    String firstName;
    
    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    String lastName;
    
    UserRole role;
}
