package com.healthapp.ehr.domain;

import com.healthapp.ehr.domain.enums.VitalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Vital sign reading stored in MongoDB.
 * Designed for time-series queries.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "vitals")
@CompoundIndex(name = "patient_type_time_idx", def = "{'patientId': 1, 'vitalType': 1, 'recordedAt': -1}")
@CompoundIndex(name = "patient_time_idx", def = "{'patientId': 1, 'recordedAt': -1}")
public class VitalReading {

    @Id
    private String id;

    @Indexed
    private UUID patientId;

    private VitalType vitalType;

    private Double value;
    private String unit;

    // For blood pressure, store both values
    private Double secondaryValue;  // diastolic when vitalType is BLOOD_PRESSURE_SYSTOLIC

    // Context
    private String notes;
    private String source;          // MANUAL, DEVICE, CONSULTATION
    private String deviceId;
    private String deviceName;

    // Related consultation
    private UUID consultationId;
    private UUID recordedById;       // Doctor or patient
    private String recordedByName;
    private String recordedByRole;   // DOCTOR, PATIENT, NURSE

    // Flags
    private Boolean isAbnormal;
    private String abnormalReason;

    @Indexed
    private LocalDateTime recordedAt;

    @CreatedDate
    private LocalDateTime createdAt;
}
