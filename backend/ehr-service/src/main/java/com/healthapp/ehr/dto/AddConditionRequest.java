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
 * Request to add a chronic condition.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddConditionRequest {

    @NotNull(message = "Patient ID is required")
    private UUID patientId;

    @NotBlank(message = "Condition name is required")
    private String conditionName;

    private String icdCode;
    private String description;

    private Severity severity;
    private String status;          // ACTIVE, CONTROLLED, RESOLVED, IN_REMISSION

    private LocalDateTime diagnosedDate;

    private UUID diagnosedById;
    private String diagnosedByName;
    private String diagnosedAtFacility;

    private List<String> currentMedications;
    private String treatmentNotes;

    private List<String> monitoringParameters;
    private Integer followUpIntervalDays;
}
