package com.healthapp.prescription.domain.enums;

/**
 * Status of a prescription throughout its lifecycle.
 */
public enum PrescriptionStatus {
    DRAFT,              // Doctor is still working on it
    SIGNED,             // Digitally signed, ready for dispensing
    DISPENSED,          // Pharmacy has dispensed all items
    PARTIALLY_DISPENSED, // Some items dispensed
    EXPIRED,            // Validity period ended
    CANCELLED           // Cancelled by doctor
}
