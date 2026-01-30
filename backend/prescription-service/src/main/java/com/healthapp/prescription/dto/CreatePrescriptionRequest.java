package com.healthapp.prescription.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePrescriptionRequest {

    private UUID consultationId;
    
    private UUID appointmentId;
    
    @NotNull(message = "Patient ID is required")
    private UUID patientId;
    
    @NotNull(message = "Doctor ID is required")
    private UUID doctorId;
    
    private LocalDate prescriptionDate;
    
    private LocalDate validUntil;
    
    @NotBlank(message = "Diagnosis is required")
    private String diagnosis;
    
    private String chiefComplaints;
    
    private String clinicalNotes;
    
    private String generalAdvice;
    
    private String dietAdvice;
    
    private LocalDate followUpDate;
    
    private String followUpNotes;
    
    private List<String> labTestsRecommended;
    
    @NotEmpty(message = "At least one medicine is required")
    @Valid
    private List<PrescriptionItemRequest> items;
    
    private UUID templateId;
}
