package com.healthapp.ehr.controller;

import com.healthapp.ehr.dto.PatientHealthSummary;
import com.healthapp.ehr.service.PatientSummaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * REST controller for patient health summaries.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/summary")
@RequiredArgsConstructor
public class PatientSummaryController {

    private final PatientSummaryService summaryService;

    /**
     * Get comprehensive health summary for a patient.
     */
    @GetMapping("/patient/{patientId}")
    public Mono<ResponseEntity<PatientHealthSummary>> getPatientSummary(@PathVariable UUID patientId) {
        return summaryService.getPatientSummary(patientId)
                .map(ResponseEntity::ok);
    }

    /**
     * Get emergency summary for a patient.
     */
    @GetMapping("/patient/{patientId}/emergency")
    public Mono<ResponseEntity<PatientHealthSummary>> getEmergencySummary(@PathVariable UUID patientId) {
        return summaryService.getEmergencySummary(patientId)
                .map(ResponseEntity::ok);
    }
}
