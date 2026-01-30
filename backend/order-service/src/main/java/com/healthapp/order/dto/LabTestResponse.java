package com.healthapp.order.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Response DTO for lab test.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabTestResponse {

    private UUID id;

    private String testCode;

    private String name;

    private String shortName;

    private String description;

    private UUID categoryId;

    private String categoryName;

    private String sampleType;

    private String sampleVolume;

    private Boolean fastingRequired;

    private Integer fastingHours;

    private String preparationInstructions;

    private String reportAvailableIn;

    private List<String> parameters;

    private BigDecimal basePrice;

    private BigDecimal mrp;

    private Boolean isPopular;

    private Boolean requiresDoctorReferral;

    private Boolean homeCollectionAvailable;
}
