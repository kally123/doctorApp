package com.healthapp.ehr.dto;

import com.healthapp.ehr.domain.enums.DocumentType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Request to upload a document (metadata only, file uploaded separately).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadDocumentRequest {

    @NotNull(message = "Patient ID is required")
    private UUID patientId;

    @NotNull(message = "Document type is required")
    private DocumentType documentType;

    @NotNull(message = "File name is required")
    private String fileName;

    private String title;
    private String description;

    private LocalDateTime documentDate;
    private String issuingFacility;
    private String issuingDoctor;
    private UUID issuingDoctorId;

    private String healthRecordId;
    private UUID consultationId;

    private List<String> tags;
    private Boolean isConfidential;
}
