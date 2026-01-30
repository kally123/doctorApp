package com.healthapp.ehr.service;

import com.healthapp.ehr.domain.RecordShare;
import com.healthapp.ehr.domain.enums.AccessLevel;
import com.healthapp.ehr.dto.ShareRecordRequest;
import com.healthapp.ehr.event.EhrEventPublisher;
import com.healthapp.ehr.repository.RecordShareRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Service for managing record sharing between patients and healthcare providers.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RecordSharingService {

    private final RecordShareRepository shareRepository;
    private final EhrEventPublisher eventPublisher;

    /**
     * Share records with a healthcare provider.
     */
    public Mono<RecordShare> shareRecords(UUID patientId, ShareRecordRequest request) {
        log.info("Sharing records from patient: {} with: {}", patientId, request.getSharedWithId());

        // Check for existing share
        return shareRepository.findByPatientIdAndSharedWithIdAndIsActiveTrue(patientId, request.getSharedWithId())
                .flatMap(existing -> {
                    // Update existing share
                    existing.setAccessLevel(request.getAccessLevel());
                    existing.setRecordTypes(request.getRecordTypes());
                    existing.setSpecificRecordIds(request.getSpecificRecordIds());
                    existing.setValidFrom(request.getValidFrom() != null ? request.getValidFrom() : LocalDateTime.now());
                    existing.setValidUntil(request.getValidUntil());
                    return shareRepository.save(existing);
                })
                .switchIfEmpty(Mono.defer(() -> {
                    // Create new share
                    RecordShare share = RecordShare.builder()
                            .patientId(patientId)
                            .sharedWithId(request.getSharedWithId())
                            .sharedWithName(request.getSharedWithName())
                            .sharedWithRole(request.getSharedWithRole())
                            .accessLevel(request.getAccessLevel())
                            .recordTypes(request.getRecordTypes())
                            .specificRecordIds(request.getSpecificRecordIds())
                            .validFrom(request.getValidFrom() != null ? request.getValidFrom() : LocalDateTime.now())
                            .validUntil(request.getValidUntil())
                            .isActive(true)
                            .isRevoked(false)
                            .accessCount(0)
                            .build();

                    return shareRepository.save(share);
                }))
                .doOnSuccess(share -> eventPublisher.publishRecordShared(
                        patientId, share.getId(), request.getSharedWithId(), patientId));
    }

    /**
     * Revoke a share.
     */
    public Mono<RecordShare> revokeShare(UUID patientId, String shareId, String reason) {
        return shareRepository.findById(shareId)
                .filter(share -> share.getPatientId().equals(patientId))
                .flatMap(share -> {
                    share.setIsActive(false);
                    share.setIsRevoked(true);
                    share.setRevokedAt(LocalDateTime.now());
                    share.setRevokeReason(reason);
                    return shareRepository.save(share);
                })
                .doOnSuccess(share -> eventPublisher.publishShareRevoked(patientId, share.getId(), patientId));
    }

    /**
     * Get active shares for a patient.
     */
    public Flux<RecordShare> getPatientShares(UUID patientId) {
        return shareRepository.findByPatientIdAndIsActiveTrue(patientId);
    }

    /**
     * Get shares available to a doctor.
     */
    public Flux<RecordShare> getDoctorShares(UUID doctorId) {
        return shareRepository.findBySharedWithIdAndIsActiveTrue(doctorId);
    }

    /**
     * Check if a doctor has access to a patient's records.
     */
    public Mono<Boolean> hasAccess(UUID patientId, UUID doctorId) {
        return shareRepository.findActiveShare(patientId, doctorId, LocalDateTime.now())
                .map(share -> true)
                .defaultIfEmpty(false);
    }

    /**
     * Get active share between patient and doctor.
     */
    public Mono<RecordShare> getActiveShare(UUID patientId, UUID doctorId) {
        return shareRepository.findActiveShare(patientId, doctorId, LocalDateTime.now());
    }

    /**
     * Record access to shared records.
     */
    public Mono<Void> recordAccess(String shareId) {
        return shareRepository.findById(shareId)
                .flatMap(share -> {
                    share.setAccessCount(share.getAccessCount() + 1);
                    share.setLastAccessedAt(LocalDateTime.now());
                    return shareRepository.save(share);
                })
                .then();
    }

    /**
     * Expire old shares (scheduled job).
     */
    @Scheduled(cron = "0 0 * * * *") // Every hour
    public void expireShares() {
        log.info("Running share expiration job");
        shareRepository.findExpiredShares(LocalDateTime.now())
                .flatMap(share -> {
                    share.setIsActive(false);
                    return shareRepository.save(share);
                })
                .doOnNext(share -> log.info("Expired share: {} for patient: {}", share.getId(), share.getPatientId()))
                .subscribe();
    }
}
