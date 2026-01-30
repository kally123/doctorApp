package com.healthapp.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

/**
 * Request DTO for updating user profile.
 */
@Value
@Builder
public class UpdateProfileRequest {
    
    @Size(max = 100, message = "First name must not exceed 100 characters")
    String firstName;
    
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    String lastName;
    
    @Size(max = 200, message = "Display name must not exceed 200 characters")
    String displayName;
    
    @Size(max = 500, message = "Avatar URL must not exceed 500 characters")
    String avatarUrl;
    
    LocalDate dateOfBirth;
    
    @Pattern(regexp = "^(MALE|FEMALE|OTHER|PREFER_NOT_TO_SAY)?$", message = "Invalid gender value")
    String gender;
}
