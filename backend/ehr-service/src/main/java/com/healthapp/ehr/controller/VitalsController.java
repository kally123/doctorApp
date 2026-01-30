package com.healthapp.ehr.controller;

import com.healthapp.ehr.domain.enums.VitalType;
import com.healthapp.ehr.dto.RecordVitalRequest;
import com.healthapp.ehr.dto.VitalResponse;
import com.healthapp.ehr.dto.VitalStatisticsResponse;
import com.healthapp.ehr.service.VitalsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * REST controller for vital signs.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/vitals")
@RequiredArgsConstructor
public class VitalsController {

    private final VitalsService vitalsService;

    /**
     * Record a vital reading.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<VitalResponse> recordVital(@Valid @RequestBody RecordVitalRequest request) {
        return vitalsService.recordVital(request);
    }

    /**
     * Record blood pressure.
     */
    @PostMapping("/blood-pressure")
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<VitalResponse> recordBloodPressure(
            @RequestParam UUID patientId,
            @RequestParam Double systolic,
            @RequestParam Double diastolic,
            @RequestParam(required = false) String notes,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader(value = "X-User-Name", required = false) String userName) {
        return vitalsService.recordBloodPressure(patientId, systolic, diastolic, notes, userId, userName);
    }

    /**
     * Get all vitals for a patient.
     */
    @GetMapping("/patient/{patientId}")
    public Flux<VitalResponse> getPatientVitals(@PathVariable UUID patientId) {
        return vitalsService.getPatientVitals(patientId);
    }

    /**
     * Get vitals by type.
     */
    @GetMapping("/patient/{patientId}/type/{vitalType}")
    public Flux<VitalResponse> getPatientVitalsByType(
            @PathVariable UUID patientId,
            @PathVariable VitalType vitalType) {
        return vitalsService.getPatientVitalsByType(patientId, vitalType);
    }

    /**
     * Get vitals in a time range.
     */
    @GetMapping("/patient/{patientId}/type/{vitalType}/range")
    public Flux<VitalResponse> getPatientVitalsInRange(
            @PathVariable UUID patientId,
            @PathVariable VitalType vitalType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return vitalsService.getPatientVitalsInRange(patientId, vitalType, start, end);
    }

    /**
     * Get latest vitals for a patient.
     */
    @GetMapping("/patient/{patientId}/latest")
    public Flux<VitalResponse> getLatestVitals(@PathVariable UUID patientId) {
        return vitalsService.getLatestVitals(patientId);
    }

    /**
     * Get abnormal vitals for a patient.
     */
    @GetMapping("/patient/{patientId}/abnormal")
    public Flux<VitalResponse> getAbnormalVitals(@PathVariable UUID patientId) {
        return vitalsService.getAbnormalVitals(patientId);
    }

    /**
     * Get vital statistics.
     */
    @GetMapping("/patient/{patientId}/type/{vitalType}/statistics")
    public Mono<ResponseEntity<VitalStatisticsResponse>> getVitalStatistics(
            @PathVariable UUID patientId,
            @PathVariable VitalType vitalType) {
        return vitalsService.getVitalStatistics(patientId, vitalType)
                .map(ResponseEntity::ok);
    }

    /**
     * Get vital statistics in a period.
     */
    @GetMapping("/patient/{patientId}/type/{vitalType}/statistics/range")
    public Mono<ResponseEntity<VitalStatisticsResponse>> getVitalStatisticsInPeriod(
            @PathVariable UUID patientId,
            @PathVariable VitalType vitalType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return vitalsService.getVitalStatisticsInPeriod(patientId, vitalType, start, end)
                .map(ResponseEntity::ok);
    }
}
