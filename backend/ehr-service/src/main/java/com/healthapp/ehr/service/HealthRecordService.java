package com.healthapp.ehr.service;

import com.healthapp.ehr.domain.HealthRecord;
import com.healthapp.ehr.domain.enums.RecordType;
import com.healthapp.ehr.dto.CreateHealthRecordRequest;
import com.healthapp.ehr.dto.HealthRecordResponse;
import com.healthapp.ehr.event.EhrEventPublisher;
import com.healthapp.ehr.repository.HealthRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Service for managing health records.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HealthRecordService {

    private final HealthRecordRepository healthRecordRepository;
    private final EhrEventPublisher eventPublisher;

    /**
     * Create a new health record.
     */
    public Mono<HealthRecordResponse> createRecord(CreateHealthRecordRequest request, UUID createdBy) {
        log.info("Creating health record for patient: {}, type: {}", request.getPatientId(), request.getRecordType());

        HealthRecord record = HealthRecord.builder()
                .patientId(request.getPatientId())
                .recordType(request.getRecordType())
                .title(request.getTitle())
                .summary(request.getSummary())
                .recordDate(request.getRecordDate() != null ? request.getRecordDate() : LocalDateTime.now())
                .consultationId(request.getConsultationId())
                .doctorId(request.getDoctorId())
                .doctorName(request.getDoctorName())
                .facilityName(request.getFacilityName())
                .data(request.getData())
                .diagnoses(request.getDiagnoses())
                .medications(request.getMedications())
                .procedures(request.getProcedures())
                .documentIds(request.getDocumentIds())
                .tags(request.getTags())
                .isConfidential(request.getIsConfidential() != null ? request.getIsConfidential() : false)
                .isArchived(false)
                .createdBy(createdBy)
                .build();

        return healthRecordRepository.save(record)
                .doOnSuccess(saved -> eventPublisher.publishRecordCreated(
                        saved.getPatientId(), saved.getId(), saved.getRecordType().name(), createdBy))
                .map(this::toResponse);
    }

    /**
     * Get a health record by ID.
     */
    public Mono<HealthRecordResponse> getRecord(String recordId) {
        return healthRecordRepository.findById(recordId)
                .map(this::toResponse);
    }

    /**
     * Get all records for a patient.
     */
    public Flux<HealthRecordResponse> getPatientRecords(UUID patientId) {
        return healthRecordRepository.findByPatientIdOrderByRecordDateDesc(patientId)
                .map(this::toResponse);
    }

    /**
     * Get records for a patient filtered by type.
     */
    public Flux<HealthRecordResponse> getPatientRecordsByType(UUID patientId, RecordType recordType) {
        return healthRecordRepository.findByPatientIdAndRecordTypeOrderByRecordDateDesc(patientId, recordType)
                .map(this::toResponse);
    }

    /**
     * Get records for a patient in a date range.
     */
    public Flux<HealthRecordResponse> getPatientRecordsByDateRange(
            UUID patientId, LocalDateTime startDate, LocalDateTime endDate) {
        return healthRecordRepository.findByPatientIdAndRecordDateBetweenOrderByRecordDateDesc(
                        patientId, startDate, endDate)
                .map(this::toResponse);
    }

    /**
     * Get records for a consultation.
     */
    public Flux<HealthRecordResponse> getConsultationRecords(UUID consultationId) {
        return healthRecordRepository.findByConsultationId(consultationId)
                .map(this::toResponse);
    }

    /**
     * Search records by tags.
     */
    public Flux<HealthRecordResponse> searchByTags(UUID patientId, List<String> tags) {
        return healthRecordRepository.findByPatientIdAndTagsContaining(patientId, tags)
                .map(this::toResponse);
    }

    /**
     * Update a health record.
     */
    public Mono<HealthRecordResponse> updateRecord(String recordId, CreateHealthRecordRequest request, UUID updatedBy) {
        return healthRecordRepository.findById(recordId)
                .flatMap(existing -> {
                    if (request.getTitle() != null) existing.setTitle(request.getTitle());
                    if (request.getSummary() != null) existing.setSummary(request.getSummary());
                    if (request.getData() != null) existing.setData(request.getData());
                    if (request.getDiagnoses() != null) existing.setDiagnoses(request.getDiagnoses());
                    if (request.getMedications() != null) existing.setMedications(request.getMedications());
                    if (request.getProcedures() != null) existing.setProcedures(request.getProcedures());
                    if (request.getDocumentIds() != null) existing.setDocumentIds(request.getDocumentIds());
                    if (request.getTags() != null) existing.setTags(request.getTags());
                    return healthRecordRepository.save(existing);
                })
                .doOnSuccess(updated -> eventPublisher.publishRecordUpdated(
                        updated.getPatientId(), updated.getId(), updated.getRecordType().name(), updatedBy))
                .map(this::toResponse);
    }

    /**
     * Archive a health record.
     */
    public Mono<Void> archiveRecord(String recordId) {
        return healthRecordRepository.findById(recordId)
                .flatMap(record -> {
                    record.setIsArchived(true);
                    return healthRecordRepository.save(record);
                })
                .then();
    }

    /**
     * Get shareable records (non-confidential, non-archived).
     */
    public Flux<HealthRecordResponse> getShareableRecords(UUID patientId) {
        return healthRecordRepository.findShareableRecords(patientId)
                .map(this::toResponse);
    }

    private HealthRecordResponse toResponse(HealthRecord record) {
        return HealthRecordResponse.builder()
                .id(record.getId())
                .patientId(record.getPatientId())
                .recordType(record.getRecordType())
                .title(record.getTitle())
                .summary(record.getSummary())
                .recordDate(record.getRecordDate())
                .consultationId(record.getConsultationId())
                .doctorId(record.getDoctorId())
                .doctorName(record.getDoctorName())
                .facilityName(record.getFacilityName())
                .data(record.getData())
                .diagnoses(record.getDiagnoses())
                .medications(record.getMedications())
                .procedures(record.getProcedures())
                .documentIds(record.getDocumentIds())
                .tags(record.getTags())
                .isConfidential(record.getIsConfidential())
                .isArchived(record.getIsArchived())
                .createdAt(record.getCreatedAt())
                .updatedAt(record.getUpdatedAt())
                .createdBy(record.getCreatedBy())
                .build();
    }
}
