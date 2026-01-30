package com.healthapp.ehr.dto;

import com.healthapp.ehr.domain.HealthRecord.DiagnosisInfo;
import com.healthapp.ehr.domain.HealthRecord.MedicationInfo;
import com.healthapp.ehr.domain.HealthRecord.ProcedureInfo;
import com.healthapp.ehr.domain.enums.RecordType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Health record response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthRecordResponse {

    private String id;
    private UUID patientId;
    private RecordType recordType;
    private String title;
    private String summary;
    private LocalDateTime recordDate;

    private UUID consultationId;
    private UUID doctorId;
    private String doctorName;
    private String facilityName;

    private Map<String, Object> data;

    private List<DiagnosisInfo> diagnoses;
    private List<MedicationInfo> medications;
    private List<ProcedureInfo> procedures;

    private List<String> documentIds;
    private List<String> tags;

    private Boolean isConfidential;
    private Boolean isArchived;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
}
