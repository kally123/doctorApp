package com.healthapp.common.event;

/**
 * Event type constants for all domain events.
 */
public final class EventTypes {
    
    private EventTypes() {}
    
    // User Events
    public static final String USER_REGISTERED = "user.registered.v1";
    public static final String USER_VERIFIED = "user.verified.v1";
    public static final String USER_UPDATED = "user.updated.v1";
    public static final String USER_DELETED = "user.deleted.v1";
    public static final String USER_PASSWORD_RESET = "user.password-reset.v1";
    public static final String USER_LOGIN = "user.login.v1";
    
    // Doctor Events
    public static final String DOCTOR_REGISTERED = "doctor.registered.v1";
    public static final String DOCTOR_PROFILE_UPDATED = "doctor.profile-updated.v1";
    public static final String DOCTOR_VERIFIED = "doctor.verified.v1";
    public static final String DOCTOR_AVAILABILITY_UPDATED = "doctor.availability-updated.v1";
    public static final String DOCTOR_QUALIFICATION_ADDED = "doctor.qualification-added.v1";
    
    // Appointment Events
    public static final String APPOINTMENT_BOOKED = "appointment.booked.v1";
    public static final String APPOINTMENT_CONFIRMED = "appointment.confirmed.v1";
    public static final String APPOINTMENT_CANCELLED = "appointment.cancelled.v1";
    public static final String APPOINTMENT_RESCHEDULED = "appointment.rescheduled.v1";
    public static final String APPOINTMENT_COMPLETED = "appointment.completed.v1";
    public static final String APPOINTMENT_NO_SHOW = "appointment.no-show.v1";
    
    // Consultation Events
    public static final String CONSULTATION_STARTED = "consultation.started.v1";
    public static final String CONSULTATION_ENDED = "consultation.ended.v1";
    
    // Notification Events
    public static final String NOTIFICATION_REQUESTED = "notification.requested.v1";
    public static final String NOTIFICATION_SENT = "notification.sent.v1";
    
    // Audit Events
    public static final String AUDIT_LOG = "audit.log.v1";
}
