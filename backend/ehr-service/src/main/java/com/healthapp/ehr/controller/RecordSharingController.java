package com.healthapp.ehr.controller;

import com.healthapp.ehr.domain.RecordShare;
import com.healthapp.ehr.dto.ShareRecordRequest;
import com.healthapp.ehr.service.RecordSharingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * REST controller for record sharing.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/shares")
@RequiredArgsConstructor
public class RecordSharingController {

    private final RecordSharingService sharingService;

    /**
     * Share records with a healthcare provider.
     */
    @PostMapping("/patient/{patientId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<RecordShare> shareRecords(
            @PathVariable UUID patientId,
            @Valid @RequestBody ShareRecordRequest request) {
        return sharingService.shareRecords(patientId, request);
    }

    /**
     * Revoke a share.
     */
    @PostMapping("/{shareId}/revoke")
    public Mono<ResponseEntity<RecordShare>> revokeShare(
            @PathVariable String shareId,
            @RequestHeader("X-Patient-Id") UUID patientId,
            @RequestParam(required = false) String reason) {
        return sharingService.revokeShare(patientId, shareId, reason)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Get active shares for a patient.
     */
    @GetMapping("/patient/{patientId}")
    public Flux<RecordShare> getPatientShares(@PathVariable UUID patientId) {
        return sharingService.getPatientShares(patientId);
    }

    /**
     * Get shares available to a doctor.
     */
    @GetMapping("/doctor/{doctorId}")
    public Flux<RecordShare> getDoctorShares(@PathVariable UUID doctorId) {
        return sharingService.getDoctorShares(doctorId);
    }

    /**
     * Check if a doctor has access to a patient's records.
     */
    @GetMapping("/check-access")
    public Mono<AccessCheckResponse> checkAccess(
            @RequestParam UUID patientId,
            @RequestParam UUID doctorId) {
        return sharingService.hasAccess(patientId, doctorId)
                .map(hasAccess -> new AccessCheckResponse(patientId, doctorId, hasAccess));
    }

    /**
     * Get active share between patient and doctor.
     */
    @GetMapping("/active")
    public Mono<ResponseEntity<RecordShare>> getActiveShare(
            @RequestParam UUID patientId,
            @RequestParam UUID doctorId) {
        return sharingService.getActiveShare(patientId, doctorId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    record AccessCheckResponse(UUID patientId, UUID doctorId, boolean hasAccess) {}
}
