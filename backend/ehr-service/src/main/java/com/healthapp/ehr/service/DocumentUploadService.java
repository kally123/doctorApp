package com.healthapp.ehr.service;

import com.healthapp.ehr.domain.MedicalDocument;
import com.healthapp.ehr.domain.enums.DocumentType;
import com.healthapp.ehr.dto.DocumentResponse;
import com.healthapp.ehr.dto.UploadDocumentRequest;
import com.healthapp.ehr.event.EhrEventPublisher;
import com.healthapp.ehr.repository.MedicalDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Service for managing medical documents.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentUploadService {

    private final MedicalDocumentRepository documentRepository;
    private final S3AsyncClient s3AsyncClient;
    private final S3Presigner s3Presigner;
    private final EhrEventPublisher eventPublisher;
    private final Tika tika = new Tika();

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.presigned-url-expiry:3600}")
    private int presignedUrlExpiry;

    @Value("${document.allowed-types:application/pdf,image/jpeg,image/png,image/gif,application/msword}")
    private List<String> allowedTypes;

    @Value("${document.max-size-mb:50}")
    private int maxSizeMb;

    /**
     * Upload a document to S3 and save metadata.
     */
    public Mono<DocumentResponse> uploadDocument(
            UploadDocumentRequest request, 
            FilePart filePart, 
            UUID uploadedBy) {
        
        log.info("Uploading document for patient: {}, type: {}", 
                request.getPatientId(), request.getDocumentType());

        return filePart.content()
                .reduce(DataBuffer::write)
                .flatMap(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);

                    // Validate file
                    String detectedType = tika.detect(bytes);
                    if (!allowedTypes.contains(detectedType)) {
                        return Mono.error(new IllegalArgumentException(
                                "File type not allowed: " + detectedType));
                    }

                    if (bytes.length > maxSizeMb * 1024 * 1024) {
                        return Mono.error(new IllegalArgumentException(
                                "File size exceeds maximum allowed: " + maxSizeMb + "MB"));
                    }

                    // Generate S3 key
                    String extension = getFileExtension(request.getFileName());
                    String s3Key = String.format("documents/%s/%s/%s_%s%s",
                            request.getPatientId(),
                            request.getDocumentType().name().toLowerCase(),
                            System.currentTimeMillis(),
                            UUID.randomUUID().toString().substring(0, 8),
                            extension);

                    // Upload to S3
                    return uploadToS3(s3Key, bytes, detectedType)
                            .then(saveDocumentMetadata(request, s3Key, detectedType, bytes.length, uploadedBy));
                });
    }

    /**
     * Get document by ID with presigned download URL.
     */
    public Mono<DocumentResponse> getDocument(String documentId) {
        return documentRepository.findById(documentId)
                .filter(doc -> !doc.getIsDeleted())
                .map(this::toResponseWithUrl);
    }

    /**
     * Get all documents for a patient.
     */
    public Flux<DocumentResponse> getPatientDocuments(UUID patientId) {
        return documentRepository.findByPatientIdAndIsDeletedFalseOrderByCreatedAtDesc(patientId)
                .map(this::toResponseWithUrl);
    }

    /**
     * Get documents by type.
     */
    public Flux<DocumentResponse> getPatientDocumentsByType(UUID patientId, DocumentType documentType) {
        return documentRepository.findByPatientIdAndDocumentTypeAndIsDeletedFalse(patientId, documentType)
                .map(this::toResponseWithUrl);
    }

    /**
     * Get documents for a consultation.
     */
    public Flux<DocumentResponse> getConsultationDocuments(UUID consultationId) {
        return documentRepository.findByConsultationIdAndIsDeletedFalse(consultationId)
                .map(this::toResponseWithUrl);
    }

    /**
     * Delete a document (soft delete).
     */
    public Mono<Void> deleteDocument(String documentId, UUID deletedBy) {
        return documentRepository.findById(documentId)
                .flatMap(doc -> {
                    doc.setIsDeleted(true);
                    return documentRepository.save(doc);
                })
                .then();
    }

    /**
     * Permanently delete a document from S3.
     */
    public Mono<Void> permanentlyDeleteDocument(String documentId) {
        return documentRepository.findById(documentId)
                .flatMap(doc -> {
                    DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                            .bucket(bucket)
                            .key(doc.getS3Key())
                            .build();

                    return Mono.fromFuture(s3AsyncClient.deleteObject(deleteRequest))
                            .then(documentRepository.delete(doc));
                });
    }

    private Mono<Void> uploadToS3(String key, byte[] content, String contentType) {
        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .build();

        return Mono.fromFuture(
                s3AsyncClient.putObject(putRequest, AsyncRequestBody.fromBytes(content))
        ).then();
    }

    private Mono<DocumentResponse> saveDocumentMetadata(
            UploadDocumentRequest request, 
            String s3Key, 
            String contentType, 
            long fileSize,
            UUID uploadedBy) {

        MedicalDocument document = MedicalDocument.builder()
                .patientId(request.getPatientId())
                .documentType(request.getDocumentType())
                .title(request.getTitle())
                .description(request.getDescription())
                .fileName(request.getFileName())
                .fileExtension(getFileExtension(request.getFileName()))
                .contentType(contentType)
                .fileSize(fileSize)
                .s3Bucket(bucket)
                .s3Key(s3Key)
                .documentDate(request.getDocumentDate())
                .issuingFacility(request.getIssuingFacility())
                .issuingDoctor(request.getIssuingDoctor())
                .issuingDoctorId(request.getIssuingDoctorId())
                .healthRecordId(request.getHealthRecordId())
                .consultationId(request.getConsultationId())
                .tags(request.getTags())
                .isScanned(false)
                .isSafe(null)
                .isConfidential(request.getIsConfidential() != null ? request.getIsConfidential() : false)
                .isArchived(false)
                .isDeleted(false)
                .uploadedBy(uploadedBy)
                .build();

        return documentRepository.save(document)
                .doOnSuccess(saved -> eventPublisher.publishDocumentUploaded(
                        saved.getPatientId(), saved.getId(), saved.getDocumentType().name(), uploadedBy))
                .map(this::toResponseWithUrl);
    }

    private String generatePresignedUrl(String s3Key) {
        try {
            GetObjectRequest getRequest = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(s3Key)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofSeconds(presignedUrlExpiry))
                    .getObjectRequest(getRequest)
                    .build();

            return s3Presigner.presignGetObject(presignRequest).url().toString();
        } catch (Exception e) {
            log.error("Failed to generate presigned URL for key: {}", s3Key, e);
            return null;
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    private DocumentResponse toResponseWithUrl(MedicalDocument doc) {
        return DocumentResponse.builder()
                .id(doc.getId())
                .patientId(doc.getPatientId())
                .documentType(doc.getDocumentType())
                .title(doc.getTitle())
                .description(doc.getDescription())
                .fileName(doc.getFileName())
                .fileExtension(doc.getFileExtension())
                .contentType(doc.getContentType())
                .fileSize(doc.getFileSize())
                .downloadUrl(generatePresignedUrl(doc.getS3Key()))
                .documentDate(doc.getDocumentDate())
                .issuingFacility(doc.getIssuingFacility())
                .issuingDoctor(doc.getIssuingDoctor())
                .issuingDoctorId(doc.getIssuingDoctorId())
                .healthRecordId(doc.getHealthRecordId())
                .consultationId(doc.getConsultationId())
                .tags(doc.getTags())
                .isScanned(doc.getIsScanned())
                .isSafe(doc.getIsSafe())
                .isConfidential(doc.getIsConfidential())
                .isArchived(doc.getIsArchived())
                .uploadedBy(doc.getUploadedBy())
                .createdAt(doc.getCreatedAt())
                .updatedAt(doc.getUpdatedAt())
                .build();
    }
}
