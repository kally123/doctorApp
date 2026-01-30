package com.healthapp.ehr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Patient health summary.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientHealthSummary {

    private UUID patientId;
    private String patientName;

    // Counts
    private Long totalRecords;
    private Long totalDocuments;
    private Long activeAllergies;
    private Long activeConditions;

    // Active allergies
    private List<AllergyInfo> allergies;

    // Active conditions
    private List<ConditionInfo> conditions;

    // Latest vitals
    private List<LatestVital> latestVitals;

    // Recent records
    private List<RecentRecord> recentRecords;

    // Sharing info
    private Long activeShares;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AllergyInfo {
        private String id;
        private String allergen;
        private String allergenType;
        private String severity;
        private List<String> reactions;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConditionInfo {
        private String id;
        private String conditionName;
        private String icdCode;
        private String severity;
        private String status;
        private LocalDateTime diagnosedDate;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LatestVital {
        private String vitalType;
        private Double value;
        private String unit;
        private Double secondaryValue;
        private LocalDateTime recordedAt;
        private Boolean isAbnormal;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecentRecord {
        private String id;
        private String recordType;
        private String title;
        private String summary;
        private LocalDateTime recordDate;
        private String doctorName;
    }
}
