package com.healthapp.order.domain.enums;

/**
 * Lab booking status through its lifecycle.
 */
public enum LabBookingStatus {
    PENDING,                    // Booking created, awaiting payment
    CONFIRMED,                  // Payment done, booking confirmed
    PHLEBOTOMIST_ASSIGNED,     // Phlebotomist assigned for home collection
    SAMPLE_COLLECTED,           // Sample collected from patient
    PROCESSING,                 // Sample being processed at lab
    REPORT_READY,               // Report is ready
    COMPLETED,                  // Report delivered, booking complete
    CANCELLED                   // Booking cancelled
}
