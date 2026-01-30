package com.healthapp.ehr.domain;

import com.healthapp.ehr.domain.enums.Severity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Chronic condition or ongoing health issue.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "conditions")
public class ChronicCondition {

    @Id
    private String id;

    @Indexed
    private UUID patientId;

    private String conditionName;
    private String icdCode;         // ICD-10 code
    private String description;

    private Severity severity;
    private String status;          // ACTIVE, CONTROLLED, RESOLVED, IN_REMISSION

    private LocalDateTime diagnosedDate;
    private LocalDateTime resolvedDate;

    private UUID diagnosedById;
    private String diagnosedByName;
    private String diagnosedAtFacility;

    // Current treatment
    private List<String> currentMedications;
    private String treatmentNotes;

    // Monitoring
    private List<String> monitoringParameters;  // What vitals to track
    private Integer followUpIntervalDays;

    private Boolean isActive;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
