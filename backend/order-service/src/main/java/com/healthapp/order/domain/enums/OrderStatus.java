package com.healthapp.order.domain.enums;

/**
 * Status of an order through its lifecycle.
 */
public enum OrderStatus {
    CART,                   // In cart, not yet placed
    PENDING_PAYMENT,        // Awaiting payment
    PAYMENT_FAILED,         // Payment failed
    CONFIRMED,              // Payment successful, order confirmed
    PROCESSING,             // Being processed by partner
    PACKED,                 // Items packed
    SHIPPED,                // Handed to delivery
    OUT_FOR_DELIVERY,       // With delivery agent
    DELIVERED,              // Successfully delivered
    CANCELLED,              // Cancelled by user/system
    RETURN_REQUESTED,       // Return initiated
    RETURNED,               // Return completed
    REFUNDED                // Refund processed
}
