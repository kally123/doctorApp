package com.healthapp.prescription.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionResponse {

    private UUID id;
    private String prescriptionNumber;
    private UUID consultationId;
    private UUID appointmentId;
    private UUID patientId;
    private UUID doctorId;
    
    private LocalDate prescriptionDate;
    private LocalDate validUntil;
    
    private String diagnosis;
    private String chiefComplaints;
    private String clinicalNotes;
    
    private String generalAdvice;
    private String dietAdvice;
    private LocalDate followUpDate;
    private String followUpNotes;
    
    private List<String> labTestsRecommended;
    
    private String status;
    private Instant signedAt;
    private boolean isSigned;
    
    private String pdfUrl;
    
    private List<PrescriptionItemResponse> items;
    
    private Instant createdAt;
    private Instant updatedAt;
    
    // Doctor info (populated from external service)
    private DoctorInfo doctorInfo;
    
    // Patient info (populated from external service)
    private PatientInfo patientInfo;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DoctorInfo {
        private UUID doctorId;
        private String name;
        private String specialization;
        private String registrationNumber;
        private String qualifications;
        private String phoneNumber;
        private String clinicName;
        private String clinicAddress;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PatientInfo {
        private UUID patientId;
        private String name;
        private Integer age;
        private String gender;
        private String phoneNumber;
    }
}
