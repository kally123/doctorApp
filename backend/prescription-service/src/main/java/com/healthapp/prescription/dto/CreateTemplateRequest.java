package com.healthapp.prescription.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTemplateRequest {

    @NotNull(message = "Doctor ID is required")
    private UUID doctorId;
    
    @NotBlank(message = "Template name is required")
    private String templateName;
    
    private String description;
    
    private String diagnosis;
    
    private String specialization;
    
    private List<PrescriptionItemRequest> items;
    
    private String generalAdvice;
    
    private String dietAdvice;
    
    private Boolean isPublic;
}
