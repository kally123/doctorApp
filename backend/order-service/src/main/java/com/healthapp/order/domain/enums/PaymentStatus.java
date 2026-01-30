package com.healthapp.order.domain.enums;

/**
 * Payment status for orders.
 */
public enum PaymentStatus {
    PENDING,
    COMPLETED,
    FAILED,
    REFUNDED,
    PARTIALLY_REFUNDED
}
