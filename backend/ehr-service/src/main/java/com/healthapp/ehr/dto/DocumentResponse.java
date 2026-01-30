package com.healthapp.ehr.dto;

import com.healthapp.ehr.domain.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Document response with download URL.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponse {

    private String id;
    private UUID patientId;
    private DocumentType documentType;

    private String title;
    private String description;
    private String fileName;
    private String fileExtension;
    private String contentType;
    private Long fileSize;

    private String downloadUrl;  // Presigned S3 URL
    private String thumbnailUrl; // For images

    private LocalDateTime documentDate;
    private String issuingFacility;
    private String issuingDoctor;
    private UUID issuingDoctorId;

    private String healthRecordId;
    private UUID consultationId;

    private List<String> tags;

    private Boolean isScanned;
    private Boolean isSafe;

    private Boolean isConfidential;
    private Boolean isArchived;

    private UUID uploadedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
