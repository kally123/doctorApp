package com.healthapp.ehr.controller;

import com.healthapp.ehr.domain.Allergy;
import com.healthapp.ehr.domain.ChronicCondition;
import com.healthapp.ehr.dto.AddAllergyRequest;
import com.healthapp.ehr.dto.AddConditionRequest;
import com.healthapp.ehr.service.MedicalHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * REST controller for medical history (allergies and conditions).
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/medical-history")
@RequiredArgsConstructor
public class MedicalHistoryController {

    private final MedicalHistoryService medicalHistoryService;

    // ========== Allergies ==========

    /**
     * Add an allergy.
     */
    @PostMapping("/allergies")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Allergy> addAllergy(@Valid @RequestBody AddAllergyRequest request) {
        return medicalHistoryService.addAllergy(request);
    }

    /**
     * Get all allergies for a patient.
     */
    @GetMapping("/allergies/patient/{patientId}")
    public Flux<Allergy> getPatientAllergies(@PathVariable UUID patientId) {
        return medicalHistoryService.getPatientAllergies(patientId);
    }

    /**
     * Get active allergies for a patient.
     */
    @GetMapping("/allergies/patient/{patientId}/active")
    public Flux<Allergy> getActiveAllergies(@PathVariable UUID patientId) {
        return medicalHistoryService.getActiveAllergies(patientId);
    }

    /**
     * Resolve an allergy.
     */
    @PostMapping("/allergies/{allergyId}/resolve")
    public Mono<ResponseEntity<Allergy>> resolveAllergy(
            @PathVariable String allergyId,
            @RequestParam(required = false) LocalDateTime resolvedDate) {
        return medicalHistoryService.resolveAllergy(allergyId, resolvedDate)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // ========== Chronic Conditions ==========

    /**
     * Add a chronic condition.
     */
    @PostMapping("/conditions")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ChronicCondition> addCondition(@Valid @RequestBody AddConditionRequest request) {
        return medicalHistoryService.addCondition(request);
    }

    /**
     * Get all conditions for a patient.
     */
    @GetMapping("/conditions/patient/{patientId}")
    public Flux<ChronicCondition> getPatientConditions(@PathVariable UUID patientId) {
        return medicalHistoryService.getPatientConditions(patientId);
    }

    /**
     * Get active conditions for a patient.
     */
    @GetMapping("/conditions/patient/{patientId}/active")
    public Flux<ChronicCondition> getActiveConditions(@PathVariable UUID patientId) {
        return medicalHistoryService.getActiveConditions(patientId);
    }

    /**
     * Update condition status.
     */
    @PatchMapping("/conditions/{conditionId}/status")
    public Mono<ResponseEntity<ChronicCondition>> updateConditionStatus(
            @PathVariable String conditionId,
            @RequestParam String status) {
        return medicalHistoryService.updateConditionStatus(conditionId, status)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Update condition medications.
     */
    @PatchMapping("/conditions/{conditionId}/medications")
    public Mono<ResponseEntity<ChronicCondition>> updateConditionMedications(
            @PathVariable String conditionId,
            @RequestParam List<String> medications,
            @RequestParam(required = false) String notes) {
        return medicalHistoryService.updateConditionMedications(conditionId, medications, notes)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
