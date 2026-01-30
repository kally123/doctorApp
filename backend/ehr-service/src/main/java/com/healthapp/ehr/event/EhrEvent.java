package com.healthapp.ehr.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * EHR event for Kafka publishing.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EhrEvent {

    private String eventId;
    private EventType eventType;
    private UUID patientId;
    private String recordId;
    private String recordType;
    private Map<String, Object> eventData;
    private LocalDateTime occurredAt;
    private UUID triggeredBy;

    public enum EventType {
        RECORD_CREATED,
        RECORD_UPDATED,
        RECORD_DELETED,
        DOCUMENT_UPLOADED,
        DOCUMENT_DELETED,
        VITAL_RECORDED,
        ALLERGY_ADDED,
        CONDITION_ADDED,
        RECORD_SHARED,
        SHARE_REVOKED,
        RECORD_ACCESSED
    }
}
