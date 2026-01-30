package com.healthapp.prescription.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Prescription entity - main prescription record.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("prescriptions")
public class Prescription {

    @Id
    private UUID id;
    
    // References
    @Column("consultation_id")
    private UUID consultationId;
    
    @Column("appointment_id")
    private UUID appointmentId;
    
    @Column("patient_id")
    private UUID patientId;
    
    @Column("doctor_id")
    private UUID doctorId;
    
    // Prescription details
    @Column("prescription_number")
    private String prescriptionNumber;
    
    @Column("prescription_date")
    private LocalDate prescriptionDate;
    
    @Column("valid_until")
    private LocalDate validUntil;
    
    // Clinical information
    private String diagnosis;
    
    @Column("chief_complaints")
    private String chiefComplaints;
    
    @Column("clinical_notes")
    private String clinicalNotes;
    
    // Advice and follow-up
    @Column("general_advice")
    private String generalAdvice;
    
    @Column("diet_advice")
    private String dietAdvice;
    
    @Column("follow_up_date")
    private LocalDate followUpDate;
    
    @Column("follow_up_notes")
    private String followUpNotes;
    
    // Lab tests (JSON array as string)
    @Column("lab_tests_recommended")
    private String labTestsRecommended;
    
    // Digital signature
    @Builder.Default
    private String status = "DRAFT";
    
    @Column("signed_at")
    private Instant signedAt;
    
    @Column("signature_hash")
    private String signatureHash;
    
    @Column("certificate_serial")
    private String certificateSerial;
    
    // PDF storage
    @Column("pdf_url")
    private String pdfUrl;
    
    @Column("pdf_s3_key")
    private String pdfS3Key;
    
    @Column("pdf_generated_at")
    private Instant pdfGeneratedAt;
    
    // Template
    @Column("template_id")
    private UUID templateId;
    
    // Metadata
    @CreatedDate
    @Column("created_at")
    private Instant createdAt;
    
    @LastModifiedDate
    @Column("updated_at")
    private Instant updatedAt;
    
    @Column("created_by")
    private UUID createdBy;
}
