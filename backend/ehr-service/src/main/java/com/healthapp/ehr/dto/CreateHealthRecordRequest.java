package com.healthapp.ehr.dto;

import com.healthapp.ehr.domain.HealthRecord.DiagnosisInfo;
import com.healthapp.ehr.domain.HealthRecord.MedicationInfo;
import com.healthapp.ehr.domain.HealthRecord.ProcedureInfo;
import com.healthapp.ehr.domain.enums.RecordType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Request to create a health record.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateHealthRecordRequest {

    @NotNull(message = "Patient ID is required")
    private UUID patientId;

    @NotNull(message = "Record type is required")
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
}
