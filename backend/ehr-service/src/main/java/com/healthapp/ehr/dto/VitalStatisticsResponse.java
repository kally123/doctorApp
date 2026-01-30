package com.healthapp.ehr.dto;

import com.healthapp.ehr.domain.enums.VitalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Vital statistics response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VitalStatisticsResponse {

    private UUID patientId;
    private VitalType vitalType;
    private String unit;

    private Double average;
    private Double minimum;
    private Double maximum;
    private Long count;

    private Double latestValue;
    private LocalDateTime latestRecordedAt;

    private LocalDateTime periodStart;
    private LocalDateTime periodEnd;

    // Trend analysis
    private String trend;           // INCREASING, DECREASING, STABLE
    private Double changePercent;
}
