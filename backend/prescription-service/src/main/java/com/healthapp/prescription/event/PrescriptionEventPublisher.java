package com.healthapp.prescription.event;

import com.healthapp.prescription.domain.Prescription;
import com.healthapp.prescription.domain.PrescriptionItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Publisher for prescription events to Kafka.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PrescriptionEventPublisher {

    private final KafkaTemplate<String, PrescriptionEvent> kafkaTemplate;

    @Value("${kafka.topics.prescription-events:prescription-events}")
    private String prescriptionEventsTopic;

    public void publishPrescriptionCreated(Prescription prescription, List<PrescriptionItem> items) {
        PrescriptionEvent event = buildEvent(prescription, items, PrescriptionEvent.EventType.PRESCRIPTION_CREATED);
        sendEvent(event);
    }

    public void publishPrescriptionSigned(Prescription prescription, List<PrescriptionItem> items) {
        PrescriptionEvent event = buildEvent(prescription, items, PrescriptionEvent.EventType.PRESCRIPTION_SIGNED);
        event.setPdfUrl(prescription.getPdfUrl());
        sendEvent(event);
    }

    public void publishPrescriptionDispensed(Prescription prescription, List<PrescriptionItem> items) {
        PrescriptionEvent event = buildEvent(prescription, items, PrescriptionEvent.EventType.PRESCRIPTION_DISPENSED);
        sendEvent(event);
    }

    public void publishPrescriptionCancelled(Prescription prescription) {
        PrescriptionEvent event = PrescriptionEvent.builder()
                .eventId(UUID.randomUUID())
                .eventType(PrescriptionEvent.EventType.PRESCRIPTION_CANCELLED)
                .prescriptionId(prescription.getId())
                .prescriptionNumber(prescription.getPrescriptionNumber())
                .patientId(prescription.getPatientId())
                .doctorId(prescription.getDoctorId())
                .status(prescription.getStatus())
                .build();
        sendEvent(event);
    }

    private PrescriptionEvent buildEvent(Prescription prescription, List<PrescriptionItem> items, 
                                          PrescriptionEvent.EventType eventType) {
        List<PrescriptionEvent.MedicineItem> medicineItems = items.stream()
                .map(item -> PrescriptionEvent.MedicineItem.builder()
                        .medicineId(item.getMedicineId())
                        .medicineName(item.getMedicineName())
                        .genericName(item.getGenericName())
                        .dosage(item.getDosage())
                        .frequency(item.getFrequency())
                        .duration(item.getDuration())
                        .quantity(item.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return PrescriptionEvent.builder()
                .eventId(UUID.randomUUID())
                .eventType(eventType)
                .prescriptionId(prescription.getId())
                .prescriptionNumber(prescription.getPrescriptionNumber())
                .patientId(prescription.getPatientId())
                .doctorId(prescription.getDoctorId())
                .consultationId(prescription.getConsultationId())
                .appointmentId(prescription.getAppointmentId())
                .prescriptionDate(prescription.getPrescriptionDate())
                .validUntil(prescription.getValidUntil())
                .diagnosis(prescription.getDiagnosis())
                .status(prescription.getStatus())
                .medicines(medicineItems)
                .build();
    }

    private void sendEvent(PrescriptionEvent event) {
        try {
            kafkaTemplate.send(prescriptionEventsTopic, event.getPrescriptionId().toString(), event);
            log.info("Published {} event for prescription: {}", event.getEventType(), event.getPrescriptionNumber());
        } catch (Exception e) {
            log.error("Failed to publish event: {} for prescription: {}", 
                    event.getEventType(), event.getPrescriptionNumber(), e);
        }
    }
}
