package com.healthapp.order.dto;

import com.healthapp.order.domain.enums.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Response DTO for order.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private UUID id;

    private String orderNumber;

    private UUID userId;

    private OrderType orderType;

    // Partner info
    private UUID partnerId;

    private String partnerName;

    private PartnerType partnerType;

    // Items
    private List<OrderItemResponse> items;

    // Delivery
    private AddressResponse deliveryAddress;

    private DeliveryType deliveryType;

    private LocalDate scheduledDeliveryDate;

    private String scheduledDeliverySlot;

    // Pricing
    private BigDecimal subtotal;

    private BigDecimal discountAmount;

    private String couponCode;

    private BigDecimal deliveryFee;

    private BigDecimal taxAmount;

    private BigDecimal totalAmount;

    private String currency;

    // Payment
    private PaymentStatus paymentStatus;

    private UUID paymentId;

    private String paymentMethod;

    private Instant paidAt;

    // Status
    private OrderStatus status;

    private Instant statusUpdatedAt;

    // Tracking
    private String trackingNumber;

    private String deliveryPartner;

    private Instant estimatedDelivery;

    private Instant actualDelivery;

    // Notes
    private String notes;

    // Timestamps
    private Instant createdAt;

    private Instant updatedAt;

    // Actions
    private boolean canCancel;

    private boolean canReturn;

    private boolean canReorder;
}
