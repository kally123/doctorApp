package com.healthapp.doctor.model.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

/**
 * Request DTO for creating a doctor profile.
 */
@Value
@Builder
public class CreateDoctorRequest {
    
    @NotBlank(message = "Full name is required")
    @Size(max = 200, message = "Full name must not exceed 200 characters")
    String fullName;
    
    @NotBlank(message = "Registration number is required")
    @Size(max = 100, message = "Registration number must not exceed 100 characters")
    String registrationNumber;
    
    @Size(max = 200, message = "Registration council must not exceed 200 characters")
    String registrationCouncil;
    
    @Min(value = 0, message = "Experience years cannot be negative")
    @Max(value = 70, message = "Experience years seems too high")
    Integer experienceYears;
    
    @Size(max = 2000, message = "Bio must not exceed 2000 characters")
    String bio;
    
    @DecimalMin(value = "0", message = "Consultation fee cannot be negative")
    BigDecimal consultationFee;
    
    @DecimalMin(value = "0", message = "Video consultation fee cannot be negative")
    BigDecimal videoConsultationFee;
    
    @NotEmpty(message = "At least one specialization is required")
    List<String> specializationIds;
    
    List<String> languageIds;
}
