package com.healthapp.ehr.dto;

import com.healthapp.ehr.domain.enums.VitalType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Request to record a vital reading.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordVitalRequest {

    @NotNull(message = "Patient ID is required")
    private UUID patientId;

    @NotNull(message = "Vital type is required")
    private VitalType vitalType;

    @NotNull(message = "Value is required")
    private Double value;

    private String unit;

    // For blood pressure
    private Double secondaryValue;

    private String notes;
    private String source;      // MANUAL, DEVICE, CONSULTATION
    private String deviceId;
    private String deviceName;

    private UUID consultationId;
    private UUID recordedById;
    private String recordedByName;
    private String recordedByRole;

    private LocalDateTime recordedAt;
}
