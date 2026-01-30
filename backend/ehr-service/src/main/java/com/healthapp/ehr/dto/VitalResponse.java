package com.healthapp.ehr.dto;

import com.healthapp.ehr.domain.enums.VitalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Vital reading response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VitalResponse {

    private String id;
    private UUID patientId;
    private VitalType vitalType;

    private Double value;
    private String unit;
    private Double secondaryValue;

    private String notes;
    private String source;
    private String deviceId;
    private String deviceName;

    private UUID consultationId;
    private UUID recordedById;
    private String recordedByName;
    private String recordedByRole;

    private Boolean isAbnormal;
    private String abnormalReason;

    private LocalDateTime recordedAt;
    private LocalDateTime createdAt;
}
