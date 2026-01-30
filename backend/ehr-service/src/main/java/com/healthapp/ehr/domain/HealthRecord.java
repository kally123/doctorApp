package com.healthapp.ehr.domain;

import com.healthapp.ehr.domain.enums.RecordType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Health record entity stored in MongoDB.
 * Uses flexible schema for different record types.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "health_records")
@CompoundIndex(name = "patient_date_idx", def = "{'patientId': 1, 'recordDate': -1}")
@CompoundIndex(name = "patient_type_idx", def = "{'patientId': 1, 'recordType': 1}")
public class HealthRecord {

    @Id
    private String id;

    @Indexed
    private UUID patientId;

    private RecordType recordType;

    private String title;

    private String summary;

    private LocalDateTime recordDate;

    // Reference to related entities
    private UUID consultationId;
    private UUID doctorId;
    private String doctorName;
    private String facilityName;

    // Flexible data storage for different record types
    private Map<String, Object> data;

    // Diagnoses (ICD-10 codes)
    private List<DiagnosisInfo> diagnoses;

    // Medications prescribed
    private List<MedicationInfo> medications;

    // Procedures performed
    private List<ProcedureInfo> procedures;

    // Related document IDs
    private List<String> documentIds;

    // Tags for search
    @Indexed
    private List<String> tags;

    // Visibility
    private Boolean isConfidential;
    private Boolean isArchived;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private UUID createdBy;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DiagnosisInfo {
        private String code;          // ICD-10 code
        private String name;
        private String description;
        private Boolean isPrimary;
        private LocalDateTime diagnosedDate;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MedicationInfo {
        private String medicineId;
        private String medicineName;
        private String dosage;
        private String frequency;
        private String duration;
        private String instructions;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProcedureInfo {
        private String code;          // CPT or custom code
        private String name;
        private String description;
        private LocalDateTime performedDate;
        private String outcome;
    }
}
