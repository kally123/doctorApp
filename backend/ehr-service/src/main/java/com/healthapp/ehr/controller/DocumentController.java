package com.healthapp.ehr.controller;

import com.healthapp.ehr.domain.enums.DocumentType;
import com.healthapp.ehr.dto.DocumentResponse;
import com.healthapp.ehr.dto.UploadDocumentRequest;
import com.healthapp.ehr.service.DocumentUploadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * REST controller for document management.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentUploadService documentService;

    /**
     * Upload a document.
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<DocumentResponse> uploadDocument(
            @RequestPart("metadata") @Valid UploadDocumentRequest request,
            @RequestPart("file") FilePart file,
            @RequestHeader("X-User-Id") UUID userId) {
        log.info("Uploading document for patient: {}", request.getPatientId());
        return documentService.uploadDocument(request, file, userId);
    }

    /**
     * Get a document by ID.
     */
    @GetMapping("/{documentId}")
    public Mono<ResponseEntity<DocumentResponse>> getDocument(@PathVariable String documentId) {
        return documentService.getDocument(documentId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Get all documents for a patient.
     */
    @GetMapping("/patient/{patientId}")
    public Flux<DocumentResponse> getPatientDocuments(@PathVariable UUID patientId) {
        return documentService.getPatientDocuments(patientId);
    }

    /**
     * Get documents by type.
     */
    @GetMapping("/patient/{patientId}/type/{documentType}")
    public Flux<DocumentResponse> getPatientDocumentsByType(
            @PathVariable UUID patientId,
            @PathVariable DocumentType documentType) {
        return documentService.getPatientDocumentsByType(patientId, documentType);
    }

    /**
     * Get documents for a consultation.
     */
    @GetMapping("/consultation/{consultationId}")
    public Flux<DocumentResponse> getConsultationDocuments(@PathVariable UUID consultationId) {
        return documentService.getConsultationDocuments(consultationId);
    }

    /**
     * Delete a document (soft delete).
     */
    @DeleteMapping("/{documentId}")
    public Mono<ResponseEntity<Void>> deleteDocument(
            @PathVariable String documentId,
            @RequestHeader("X-User-Id") UUID userId) {
        return documentService.deleteDocument(documentId, userId)
                .thenReturn(ResponseEntity.noContent().<Void>build());
    }
}
