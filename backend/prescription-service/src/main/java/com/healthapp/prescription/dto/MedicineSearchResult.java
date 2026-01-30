package com.healthapp.prescription.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineSearchResult {

    private String medicineId;
    private String brandName;
    private String genericName;
    private String manufacturer;
    private String category;
    private String formulation;
    private String strength;
    private Integer packSize;
    private BigDecimal price;
    private Boolean requiresPrescription;
    private Boolean isAvailable;
    private List<String> alternativeIds;
}
