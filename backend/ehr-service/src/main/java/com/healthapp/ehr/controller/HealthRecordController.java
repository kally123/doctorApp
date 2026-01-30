package com.healthapp.ehr.controller;

import com.healthapp.ehr.domain.enums.RecordType;
import com.healthapp.ehr.dto.CreateHealthRecordRequest;
import com.healthapp.ehr.dto.HealthRecordResponse;
import com.healthapp.ehr.service.HealthRecordService;
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
import java.util.List;
import java.util.UUID;

/**
 * REST controller for health records.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/records")
@RequiredArgsConstructor
public class HealthRecordController {

    private final HealthRecordService healthRecordService;

    /**
     * Create a new health record.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<HealthRecordResponse> createRecord(
            @Valid @RequestBody CreateHealthRecordRequest request,
            @RequestHeader("X-User-Id") UUID userId) {
        return healthRecordService.createRecord(request, userId);
    }

    /**
     * Get a health record by ID.
     */
    @GetMapping("/{recordId}")
    public Mono<ResponseEntity<HealthRecordResponse>> getRecord(@PathVariable String recordId) {
        return healthRecordService.getRecord(recordId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Get all records for a patient.
     */
    @GetMapping("/patient/{patientId}")
    public Flux<HealthRecordResponse> getPatientRecords(@PathVariable UUID patientId) {
        return healthRecordService.getPatientRecords(patientId);
    }

    /**
     * Get records for a patient by type.
     */
    @GetMapping("/patient/{patientId}/type/{recordType}")
    public Flux<HealthRecordResponse> getPatientRecordsByType(
            @PathVariable UUID patientId,
            @PathVariable RecordType recordType) {
        return healthRecordService.getPatientRecordsByType(patientId, recordType);
    }

    /**
     * Get records for a patient in a date range.
     */
    @GetMapping("/patient/{patientId}/range")
    public Flux<HealthRecordResponse> getPatientRecordsByDateRange(
            @PathVariable UUID patientId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return healthRecordService.getPatientRecordsByDateRange(patientId, startDate, endDate);
    }

    /**
     * Get records for a consultation.
     */
    @GetMapping("/consultation/{consultationId}")
    public Flux<HealthRecordResponse> getConsultationRecords(@PathVariable UUID consultationId) {
        return healthRecordService.getConsultationRecords(consultationId);
    }

    /**
     * Search records by tags.
     */
    @GetMapping("/patient/{patientId}/search")
    public Flux<HealthRecordResponse> searchByTags(
            @PathVariable UUID patientId,
            @RequestParam List<String> tags) {
        return healthRecordService.searchByTags(patientId, tags);
    }

    /**
     * Update a health record.
     */
    @PutMapping("/{recordId}")
    public Mono<ResponseEntity<HealthRecordResponse>> updateRecord(
            @PathVariable String recordId,
            @Valid @RequestBody CreateHealthRecordRequest request,
            @RequestHeader("X-User-Id") UUID userId) {
        return healthRecordService.updateRecord(recordId, request, userId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Archive a health record.
     */
    @PostMapping("/{recordId}/archive")
    public Mono<ResponseEntity<Void>> archiveRecord(@PathVariable String recordId) {
        return healthRecordService.archiveRecord(recordId)
                .thenReturn(ResponseEntity.noContent().<Void>build());
    }
}
