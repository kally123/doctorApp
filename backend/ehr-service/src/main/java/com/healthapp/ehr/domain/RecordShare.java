package com.healthapp.ehr.domain;

import com.healthapp.ehr.domain.enums.AccessLevel;
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
import java.util.UUID;

/**
 * Record sharing permission between patient and healthcare provider.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "record_shares")
@CompoundIndex(name = "patient_doctor_idx", def = "{'patientId': 1, 'sharedWithId': 1}")
public class RecordShare {

    @Id
    private String id;

    @Indexed
    private UUID patientId;

    @Indexed
    private UUID sharedWithId;      // Doctor ID
    private String sharedWithName;
    private String sharedWithRole;   // DOCTOR, SPECIALIST, HOSPITAL

    private AccessLevel accessLevel;

    // What records are shared
    private List<String> recordTypes;        // If null, all types
    private List<String> specificRecordIds;  // If specific records only
    
    private LocalDateTime validFrom;
    private LocalDateTime validUntil;

    private Boolean isActive;
    private Boolean isRevoked;
    private LocalDateTime revokedAt;
    private String revokeReason;

    // Audit
    private Integer accessCount;
    private LocalDateTime lastAccessedAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
