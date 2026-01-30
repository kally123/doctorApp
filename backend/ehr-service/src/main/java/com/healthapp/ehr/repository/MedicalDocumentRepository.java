package com.healthapp.ehr.repository;

import com.healthapp.ehr.domain.MedicalDocument;
import com.healthapp.ehr.domain.enums.DocumentType;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

/**
 * Repository for medical documents.
 */
public interface MedicalDocumentRepository extends ReactiveMongoRepository<MedicalDocument, String> {

    Flux<MedicalDocument> findByPatientIdAndIsDeletedFalseOrderByCreatedAtDesc(UUID patientId);

    Flux<MedicalDocument> findByPatientIdAndDocumentTypeAndIsDeletedFalse(UUID patientId, DocumentType documentType);

    Flux<MedicalDocument> findByPatientIdAndDocumentTypeInAndIsDeletedFalse(
            UUID patientId, List<DocumentType> documentTypes);

    Flux<MedicalDocument> findByHealthRecordIdAndIsDeletedFalse(String healthRecordId);

    Flux<MedicalDocument> findByConsultationIdAndIsDeletedFalse(UUID consultationId);

    Flux<MedicalDocument> findByUploadedByOrderByCreatedAtDesc(UUID uploadedBy);

    @Query("{'patientId': ?0, 'tags': {$in: ?1}, 'isDeleted': false}")
    Flux<MedicalDocument> findByPatientIdAndTagsContaining(UUID patientId, List<String> tags);

    @Query("{'isScanned': false}")
    Flux<MedicalDocument> findUnscannedDocuments();

    Mono<Long> countByPatientIdAndIsDeletedFalse(UUID patientId);

    Mono<Long> countByPatientIdAndDocumentTypeAndIsDeletedFalse(UUID patientId, DocumentType documentType);
}
