package com.healthapp.doctor.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

/**
 * DTO for doctor qualification information.
 */
@Value
@Builder
public class QualificationDto {
    String id;
    
    @NotBlank(message = "Degree is required")
    @Size(max = 100, message = "Degree must not exceed 100 characters")
    String degree;
    
    @NotBlank(message = "Institution is required")
    @Size(max = 200, message = "Institution must not exceed 200 characters")
    String institution;
    
    Integer yearOfCompletion;
    
    String certificateUrl;
    
    Boolean isVerified;
}
