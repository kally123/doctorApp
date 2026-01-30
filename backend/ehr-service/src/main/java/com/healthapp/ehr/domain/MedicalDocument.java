package com.healthapp.ehr.domain;

import com.healthapp.ehr.domain.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Medical document metadata stored in MongoDB.
 * Actual files are stored in S3.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "documents")
@CompoundIndex(name = "patient_type_idx", def = "{'patientId': 1, 'documentType': 1}")
public class MedicalDocument {

    @Id
    private String id;

    @Indexed
    private UUID patientId;

    private DocumentType documentType;

    private String title;
    private String description;

    // File information
    private String fileName;
    private String fileExtension;
    private String contentType;
    private Long fileSize;

    // S3 storage information
    private String s3Bucket;
    private String s3Key;
    private String s3Url;

    // Document metadata
    private LocalDateTime documentDate;     // Date on the document
    private String issuingFacility;
    private String issuingDoctor;
    private UUID issuingDoctorId;

    // Related health record
    private String healthRecordId;
    private UUID consultationId;

    // Tags and search
    @Indexed
    private List<String> tags;

    // OCR extracted text (for search)
    private String extractedText;

    // Virus scan status
    private Boolean isScanned;
    private Boolean isSafe;
    private LocalDateTime scannedAt;

    // Visibility
    private Boolean isConfidential;
    private Boolean isArchived;
    private Boolean isDeleted;

    @Indexed
    private UUID uploadedBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
