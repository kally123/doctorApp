package com.healthapp.prescription.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionItemRequest {

    private String medicineId;
    
    @NotBlank(message = "Medicine name is required")
    private String medicineName;
    
    private String genericName;
    
    private String manufacturer;
    
    private String strength;
    
    private String formulation;
    
    @NotBlank(message = "Dosage is required")
    private String dosage;
    
    @NotBlank(message = "Frequency is required")
    private String frequency;
    
    @NotBlank(message = "Duration is required")
    private String duration;
    
    private String timing;
    
    private String route;
    
    private Integer quantity;
    
    private String quantityUnit;
    
    private String specialInstructions;
    
    private Integer sequenceOrder;
}
