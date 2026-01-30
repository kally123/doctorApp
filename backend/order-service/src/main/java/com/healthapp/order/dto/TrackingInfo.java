package com.healthapp.order.dto;

import com.healthapp.order.domain.enums.OrderStatus;
import lombok.*;

import java.time.Instant;
import java.util.List;

/**
 * Response DTO for order tracking.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackingInfo {

    private String orderId;

    private String orderNumber;

    private OrderStatus currentStatus;

    private String statusText;

    private String trackingNumber;

    private String deliveryPartner;

    private Instant estimatedDelivery;

    private String partnerName;

    private String partnerPhone;

    private List<TrackingStep> steps;

    private boolean canCancel;

    private boolean canReturn;
}
