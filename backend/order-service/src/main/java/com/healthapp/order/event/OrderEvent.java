package com.healthapp.order.event;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Order event for Kafka publishing.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {

    private String eventType;
    private String orderId;
    private String orderNumber;
    private String userId;
    private String partnerId;
    private BigDecimal totalAmount;
    private String status;
    private String trackingNumber;
    private Instant timestamp = Instant.now();
}
