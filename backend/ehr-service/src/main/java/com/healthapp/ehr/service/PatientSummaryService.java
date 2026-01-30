package com.healthapp.ehr.service;

import com.healthapp.ehr.domain.enums.RecordType;
import com.healthapp.ehr.domain.enums.VitalType;
import com.healthapp.ehr.dto.PatientHealthSummary;
import com.healthapp.ehr.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Service for generating patient health summaries.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PatientSummaryService {

    private final HealthRecordRepository healthRecordRepository;
    private final MedicalDocumentRepository documentRepository;
    private final VitalReadingRepository vitalRepository;
    private final AllergyRepository allergyRepository;
    private final ChronicConditionRepository conditionRepository;
    private final RecordShareRepository shareRepository;

    /**
     * Generate a comprehensive health summary for a patient.
     */
    public Mono<PatientHealthSummary> getPatientSummary(UUID patientId) {
        log.info("Generating health summary for patient: {}", patientId);

        return Mono.zip(
                // Total records count
                healthRecordRepository.findByPatientIdOrderByRecordDateDesc(patientId).count(),
                // Total documents count
                documentRepository.countByPatientIdAndIsDeletedFalse(patientId),
                // Active allergies count
                allergyRepository.countByPatientIdAndIsActiveTrue(patientId),
                // Active conditions count
                conditionRepository.countByPatientIdAndIsActiveTrue(patientId),
                // Active shares count
                shareRepository.countByPatientIdAndIsActiveTrue(patientId)
        ).flatMap(counts -> {
            PatientHealthSummary.PatientHealthSummaryBuilder builder = PatientHealthSummary.builder()
                    .patientId(patientId)
                    .totalRecords(counts.getT1())
                    .totalDocuments(counts.getT2())
                    .activeAllergies(counts.getT3())
                    .activeConditions(counts.getT4())
                    .activeShares(counts.getT5());

            // Fetch allergies
            return allergyRepository.findByPatientIdAndIsActiveTrueOrderByCreatedAtDesc(patientId)
                    .map(a -> PatientHealthSummary.AllergyInfo.builder()
                            .id(a.getId())
                            .allergen(a.getAllergen())
                            .allergenType(a.getAllergenType())
                            .severity(a.getSeverity() != null ? a.getSeverity().name() : null)
                            .reactions(a.getReactions())
                            .build())
                    .collectList()
                    .flatMap(allergies -> {
                        builder.allergies(allergies);

                        // Fetch conditions
                        return conditionRepository.findByPatientIdAndIsActiveTrueOrderByDiagnosedDateDesc(patientId)
                                .map(c -> PatientHealthSummary.ConditionInfo.builder()
                                        .id(c.getId())
                                        .conditionName(c.getConditionName())
                                        .icdCode(c.getIcdCode())
                                        .severity(c.getSeverity() != null ? c.getSeverity().name() : null)
                                        .status(c.getStatus())
                                        .diagnosedDate(c.getDiagnosedDate())
                                        .build())
                                .collectList();
                    })
                    .flatMap(conditions -> {
                        builder.conditions(conditions);

                        // Fetch latest vitals
                        return Flux.fromArray(VitalType.values())
                                .flatMap(type -> vitalRepository.findFirstByPatientIdAndVitalTypeOrderByRecordedAtDesc(patientId, type))
                                .map(v -> PatientHealthSummary.LatestVital.builder()
                                        .vitalType(v.getVitalType().name())
                                        .value(v.getValue())
                                        .unit(v.getUnit())
                                        .secondaryValue(v.getSecondaryValue())
                                        .recordedAt(v.getRecordedAt())
                                        .isAbnormal(v.getIsAbnormal())
                                        .build())
                                .collectList();
                    })
                    .flatMap(vitals -> {
                        builder.latestVitals(vitals);

                        // Fetch recent records (last 10)
                        return healthRecordRepository.findByPatientIdOrderByRecordDateDesc(patientId)
                                .take(10)
                                .map(r -> PatientHealthSummary.RecentRecord.builder()
                                        .id(r.getId())
                                        .recordType(r.getRecordType().name())
                                        .title(r.getTitle())
                                        .summary(r.getSummary())
                                        .recordDate(r.getRecordDate())
                                        .doctorName(r.getDoctorName())
                                        .build())
                                .collectList();
                    })
                    .map(recentRecords -> {
                        builder.recentRecords(recentRecords);
                        return builder.build();
                    });
        });
    }

    /**
     * Get a simplified summary for emergency access.
     */
    public Mono<PatientHealthSummary> getEmergencySummary(UUID patientId) {
        log.info("Generating emergency summary for patient: {}", patientId);

        return Mono.zip(
                allergyRepository.findByPatientIdAndIsActiveTrueOrderByCreatedAtDesc(patientId)
                        .map(a -> PatientHealthSummary.AllergyInfo.builder()
                                .id(a.getId())
                                .allergen(a.getAllergen())
                                .allergenType(a.getAllergenType())
                                .severity(a.getSeverity() != null ? a.getSeverity().name() : null)
                                .reactions(a.getReactions())
                                .build())
                        .collectList(),
                conditionRepository.findByPatientIdAndIsActiveTrueOrderByDiagnosedDateDesc(patientId)
                        .map(c -> PatientHealthSummary.ConditionInfo.builder()
                                .id(c.getId())
                                .conditionName(c.getConditionName())
                                .icdCode(c.getIcdCode())
                                .severity(c.getSeverity() != null ? c.getSeverity().name() : null)
                                .status(c.getStatus())
                                .build())
                        .collectList()
        ).map(tuple -> PatientHealthSummary.builder()
                .patientId(patientId)
                .allergies(tuple.getT1())
                .conditions(tuple.getT2())
                .build());
    }
}
