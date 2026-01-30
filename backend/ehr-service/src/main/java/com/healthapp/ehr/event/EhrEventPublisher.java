package com.healthapp.ehr.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * Publisher for EHR events to Kafka.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EhrEventPublisher {

    private static final String TOPIC = "ehr-events";

    private final KafkaTemplate<String, EhrEvent> kafkaTemplate;

    public void publishRecordCreated(UUID patientId, String recordId, String recordType, UUID createdBy) {
        publish(EhrEvent.EventType.RECORD_CREATED, patientId, recordId, recordType, null, createdBy);
    }

    public void publishRecordUpdated(UUID patientId, String recordId, String recordType, UUID updatedBy) {
        publish(EhrEvent.EventType.RECORD_UPDATED, patientId, recordId, recordType, null, updatedBy);
    }

    public void publishDocumentUploaded(UUID patientId, String documentId, String documentType, UUID uploadedBy) {
        publish(EhrEvent.EventType.DOCUMENT_UPLOADED, patientId, documentId, documentType, null, uploadedBy);
    }

    public void publishVitalRecorded(UUID patientId, String vitalId, String vitalType, Double value, UUID recordedBy) {
        Map<String, Object> data = Map.of("value", value);
        publish(EhrEvent.EventType.VITAL_RECORDED, patientId, vitalId, vitalType, data, recordedBy);
    }

    public void publishAllergyAdded(UUID patientId, String allergyId, String allergen, UUID addedBy) {
        Map<String, Object> data = Map.of("allergen", allergen);
        publish(EhrEvent.EventType.ALLERGY_ADDED, patientId, allergyId, "ALLERGY", data, addedBy);
    }

    public void publishConditionAdded(UUID patientId, String conditionId, String conditionName, UUID addedBy) {
        Map<String, Object> data = Map.of("condition", conditionName);
        publish(EhrEvent.EventType.CONDITION_ADDED, patientId, conditionId, "CONDITION", data, addedBy);
    }

    public void publishRecordShared(UUID patientId, String shareId, UUID sharedWithId, UUID sharedBy) {
        Map<String, Object> data = Map.of("sharedWith", sharedWithId.toString());
        publish(EhrEvent.EventType.RECORD_SHARED, patientId, shareId, "SHARE", data, sharedBy);
    }

    public void publishShareRevoked(UUID patientId, String shareId, UUID revokedBy) {
        publish(EhrEvent.EventType.SHARE_REVOKED, patientId, shareId, "SHARE", null, revokedBy);
    }

    private void publish(EhrEvent.EventType eventType, UUID patientId, String recordId, 
                         String recordType, Map<String, Object> data, UUID triggeredBy) {
        EhrEvent event = EhrEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType(eventType)
                .patientId(patientId)
                .recordId(recordId)
                .recordType(recordType)
                .eventData(data)
                .occurredAt(LocalDateTime.now())
                .triggeredBy(triggeredBy)
                .build();

        kafkaTemplate.send(TOPIC, patientId.toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to publish EHR event: {}", eventType, ex);
                    } else {
                        log.debug("Published EHR event: {} for patient: {}", eventType, patientId);
                    }
                });
    }
}
