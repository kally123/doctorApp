package com.healthapp.ehr.service;

import com.healthapp.ehr.domain.Allergy;
import com.healthapp.ehr.domain.ChronicCondition;
import com.healthapp.ehr.dto.*;
import com.healthapp.ehr.event.EhrEventPublisher;
import com.healthapp.ehr.repository.AllergyRepository;
import com.healthapp.ehr.repository.ChronicConditionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Service for managing allergies and chronic conditions.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MedicalHistoryService {

    private final AllergyRepository allergyRepository;
    private final ChronicConditionRepository conditionRepository;
    private final EhrEventPublisher eventPublisher;

    // ========== Allergies ==========

    /**
     * Add a new allergy.
     */
    public Mono<Allergy> addAllergy(AddAllergyRequest request) {
        log.info("Adding allergy for patient: {}, allergen: {}", request.getPatientId(), request.getAllergen());

        return allergyRepository.existsByPatientIdAndAllergenIgnoreCase(request.getPatientId(), request.getAllergen())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new IllegalArgumentException("Allergy already exists for this patient"));
                    }

                    Allergy allergy = Allergy.builder()
                            .patientId(request.getPatientId())
                            .allergen(request.getAllergen())
                            .allergenType(request.getAllergenType())
                            .severity(request.getSeverity())
                            .reactions(request.getReactions())
                            .notes(request.getNotes())
                            .onsetDate(request.getOnsetDate())
                            .isActive(true)
                            .isConfirmed(request.getIsConfirmed() != null ? request.getIsConfirmed() : false)
                            .reportedById(request.getReportedById())
                            .reportedByName(request.getReportedByName())
                            .build();

                    return allergyRepository.save(allergy)
                            .doOnSuccess(saved -> eventPublisher.publishAllergyAdded(
                                    saved.getPatientId(), saved.getId(), saved.getAllergen(), saved.getReportedById()));
                });
    }

    /**
     * Get all allergies for a patient.
     */
    public Flux<Allergy> getPatientAllergies(UUID patientId) {
        return allergyRepository.findByPatientIdOrderByCreatedAtDesc(patientId);
    }

    /**
     * Get active allergies for a patient.
     */
    public Flux<Allergy> getActiveAllergies(UUID patientId) {
        return allergyRepository.findByPatientIdAndIsActiveTrueOrderByCreatedAtDesc(patientId);
    }

    /**
     * Deactivate an allergy.
     */
    public Mono<Allergy> resolveAllergy(String allergyId, LocalDateTime resolvedDate) {
        return allergyRepository.findById(allergyId)
                .flatMap(allergy -> {
                    allergy.setIsActive(false);
                    allergy.setResolvedDate(resolvedDate != null ? resolvedDate : LocalDateTime.now());
                    return allergyRepository.save(allergy);
                });
    }

    // ========== Chronic Conditions ==========

    /**
     * Add a new chronic condition.
     */
    public Mono<ChronicCondition> addCondition(AddConditionRequest request) {
        log.info("Adding condition for patient: {}, condition: {}", 
                request.getPatientId(), request.getConditionName());

        ChronicCondition condition = ChronicCondition.builder()
                .patientId(request.getPatientId())
                .conditionName(request.getConditionName())
                .icdCode(request.getIcdCode())
                .description(request.getDescription())
                .severity(request.getSeverity())
                .status(request.getStatus() != null ? request.getStatus() : "ACTIVE")
                .diagnosedDate(request.getDiagnosedDate())
                .diagnosedById(request.getDiagnosedById())
                .diagnosedByName(request.getDiagnosedByName())
                .diagnosedAtFacility(request.getDiagnosedAtFacility())
                .currentMedications(request.getCurrentMedications())
                .treatmentNotes(request.getTreatmentNotes())
                .monitoringParameters(request.getMonitoringParameters())
                .followUpIntervalDays(request.getFollowUpIntervalDays())
                .isActive(true)
                .build();

        return conditionRepository.save(condition)
                .doOnSuccess(saved -> eventPublisher.publishConditionAdded(
                        saved.getPatientId(), saved.getId(), saved.getConditionName(), saved.getDiagnosedById()));
    }

    /**
     * Get all conditions for a patient.
     */
    public Flux<ChronicCondition> getPatientConditions(UUID patientId) {
        return conditionRepository.findByPatientIdOrderByDiagnosedDateDesc(patientId);
    }

    /**
     * Get active conditions for a patient.
     */
    public Flux<ChronicCondition> getActiveConditions(UUID patientId) {
        return conditionRepository.findByPatientIdAndIsActiveTrueOrderByDiagnosedDateDesc(patientId);
    }

    /**
     * Update condition status.
     */
    public Mono<ChronicCondition> updateConditionStatus(String conditionId, String newStatus) {
        return conditionRepository.findById(conditionId)
                .flatMap(condition -> {
                    condition.setStatus(newStatus);
                    if ("RESOLVED".equals(newStatus)) {
                        condition.setResolvedDate(LocalDateTime.now());
                        condition.setIsActive(false);
                    }
                    return conditionRepository.save(condition);
                });
    }

    /**
     * Update condition medications.
     */
    public Mono<ChronicCondition> updateConditionMedications(
            String conditionId, java.util.List<String> medications, String notes) {
        return conditionRepository.findById(conditionId)
                .flatMap(condition -> {
                    condition.setCurrentMedications(medications);
                    if (notes != null) {
                        condition.setTreatmentNotes(notes);
                    }
                    return conditionRepository.save(condition);
                });
    }
}
