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
 * Patient allergy record.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "allergies")
public class Allergy {

    @Id
    private String id;

    @Indexed
    private UUID patientId;

    private String allergen;
    private String allergenType;    // DRUG, FOOD, ENVIRONMENTAL, OTHER

    private Severity severity;

    private List<String> reactions;  // Symptoms when exposed
    private String notes;

    private LocalDateTime onsetDate;
    private LocalDateTime resolvedDate;

    private Boolean isActive;
    private Boolean isConfirmed;     // Confirmed by testing

    private UUID reportedById;
    private String reportedByName;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
