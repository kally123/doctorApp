package com.healthapp.common.event;

/**
 * Kafka topic names for all event streams.
 */
public final class KafkaTopics {
    
    private KafkaTopics() {}
    
    /**
     * User-related events (registration, updates, authentication).
     */
    public static final String USER_EVENTS = "user-events";
    
    /**
     * Doctor-related events (profile updates, verification).
     */
    public static final String DOCTOR_EVENTS = "doctor-events";
    
    /**
     * Appointment-related events (booking, cancellation, completion).
     */
    public static final String APPOINTMENT_EVENTS = "appointment-events";
    
    /**
     * Notification requests (SMS, email, push).
     */
    public static final String NOTIFICATION_EVENTS = "notification-events";
    
    /**
     * Audit trail events for compliance.
     */
    public static final String AUDIT_EVENTS = "audit-events";
    
    /**
     * Consultation events (start, end, recording).
     */
    public static final String CONSULTATION_EVENTS = "consultation-events";
    
    /**
     * Payment and transaction events.
     */
    public static final String PAYMENT_EVENTS = "payment-events";
    
    /**
     * Search index update events.
     */
    public static final String SEARCH_INDEX_EVENTS = "search-index-events";
}
