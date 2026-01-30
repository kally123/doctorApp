package com.healthapp.ehr.domain.enums;

/**
 * Access levels for shared records.
 */
public enum AccessLevel {
    FULL,           // Full access to all records
    LIMITED,        // Limited access to specific records
    SUMMARY_ONLY,   // Only summary information
    EMERGENCY       // Emergency access - full access for limited time
}
