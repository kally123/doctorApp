package com.healthapp.prescription.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionItemResponse {

    private UUID id;
    private String medicineId;
    private String medicineName;
    private String genericName;
    private String manufacturer;
    
    private String strength;
    private String formulation;
    
    private String dosage;
    private String frequency;
    private String duration;
    private String timing;
    private String route;
    
    private Integer quantity;
    private String quantityUnit;
    
    private String specialInstructions;
    private Integer sequenceOrder;
    
    private Boolean isDispensed;
    private Integer dispensedQuantity;
}
