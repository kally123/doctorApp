package com.healthapp.ehr.dto;

import com.healthapp.ehr.domain.enums.Severity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Request to add an allergy.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddAllergyRequest {

    @NotNull(message = "Patient ID is required")
    private UUID patientId;

    @NotBlank(message = "Allergen is required")
    private String allergen;

    private String allergenType;    // DRUG, FOOD, ENVIRONMENTAL, OTHER

    private Severity severity;

    private List<String> reactions;
    private String notes;

    private LocalDateTime onsetDate;
    private Boolean isConfirmed;

    private UUID reportedById;
    private String reportedByName;
}
