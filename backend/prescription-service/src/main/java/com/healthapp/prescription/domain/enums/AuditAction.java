package com.healthapp.prescription.domain.enums;

/**
 * Actions tracked in prescription audit log.
 */
public enum AuditAction {
    CREATED,
    UPDATED,
    SIGNED,
    CANCELLED,
    DISPENSED,
    PARTIALLY_DISPENSED,
    VIEWED,
    DOWNLOADED,
    SHARED
}
