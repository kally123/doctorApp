package com.healthapp.prescription.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Event published when prescription lifecycle events occur.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionEvent {

    private UUID eventId;
    private EventType eventType;
    
    private UUID prescriptionId;
    private String prescriptionNumber;
    private UUID patientId;
    private UUID doctorId;
    private UUID consultationId;
    private UUID appointmentId;
    
    private LocalDate prescriptionDate;
    private LocalDate validUntil;
    private String diagnosis;
    
    private String status;
    private List<MedicineItem> medicines;
    
    private String pdfUrl;
    
    @Builder.Default
    private Instant timestamp = Instant.now();
    
    public enum EventType {
        PRESCRIPTION_CREATED,
        PRESCRIPTION_SIGNED,
        PRESCRIPTION_DISPENSED,
        PRESCRIPTION_CANCELLED,
        PRESCRIPTION_EXPIRED
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MedicineItem {
        private String medicineId;
        private String medicineName;
        private String genericName;
        private String dosage;
        private String frequency;
        private String duration;
        private Integer quantity;
    }
}
