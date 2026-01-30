package com.healthapp.order.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Response DTO for test package.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestPackageResponse {

    private UUID id;

    private String packageCode;

    private String name;

    private String description;

    private List<LabTestResponse> includedTests;

    private BigDecimal packagePrice;

    private BigDecimal individualPrice;

    private BigDecimal discountPercent;

    private BigDecimal savings;

    private Integer totalParameters;

    private List<String> sampleTypes;

    private Boolean fastingRequired;

    private String targetGender;

    private String targetAgeGroup;

    private Boolean isPopular;
}
