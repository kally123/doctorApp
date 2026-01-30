package com.healthapp.order.event;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Lab booking event for Kafka publishing.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabBookingEvent {

    private String eventType;
    private String bookingId;
    private String bookingNumber;
    private String userId;
    private String labPartnerId;
    private BigDecimal totalAmount;
    private String status;
    private String scheduledDate;
    private String scheduledSlot;
    private String reportUrl;
    private Instant timestamp = Instant.now();
}
