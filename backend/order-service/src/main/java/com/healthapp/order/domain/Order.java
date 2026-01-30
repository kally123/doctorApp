package com.healthapp.order.domain;

import com.healthapp.order.domain.enums.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Main order entity for medicine and lab test orders.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("orders")
public class Order {

    @Id
    private UUID id;

    @Column("order_number")
    private String orderNumber;

    @Column("user_id")
    private UUID userId;

    @Column("order_type")
    private OrderType orderType;

    @Column("prescription_id")
    private UUID prescriptionId;

    // Partner info
    @Column("partner_id")
    private UUID partnerId;

    @Column("partner_type")
    private PartnerType partnerType;

    @Column("partner_name")
    private String partnerName;

    @Column("partner_accepted_at")
    private Instant partnerAcceptedAt;

    // Delivery info
    @Column("delivery_address_id")
    private UUID deliveryAddressId;

    @Column("delivery_address_snapshot")
    private String deliveryAddressSnapshot; // JSON

    @Column("delivery_type")
    private DeliveryType deliveryType;

    @Column("scheduled_delivery_date")
    private LocalDate scheduledDeliveryDate;

    @Column("scheduled_delivery_slot")
    private String scheduledDeliverySlot;

    // Pricing
    @Column("subtotal")
    private BigDecimal subtotal;

    @Column("discount_amount")
    private BigDecimal discountAmount;

    @Column("coupon_code")
    private String couponCode;

    @Column("delivery_fee")
    private BigDecimal deliveryFee;

    @Column("tax_amount")
    private BigDecimal taxAmount;

    @Column("total_amount")
    private BigDecimal totalAmount;

    @Column("currency")
    private String currency;

    // Payment
    @Column("payment_status")
    private PaymentStatus paymentStatus;

    @Column("payment_id")
    private UUID paymentId;

    @Column("payment_method")
    private String paymentMethod;

    @Column("paid_at")
    private Instant paidAt;

    // Status
    @Column("status")
    private OrderStatus status;

    @Column("status_updated_at")
    private Instant statusUpdatedAt;

    // Tracking
    @Column("tracking_number")
    private String trackingNumber;

    @Column("delivery_partner")
    private String deliveryPartner;

    @Column("estimated_delivery")
    private Instant estimatedDelivery;

    @Column("actual_delivery")
    private Instant actualDelivery;

    // Notes
    @Column("notes")
    private String notes;

    @Column("internal_notes")
    private String internalNotes;

    // Timestamps
    @CreatedDate
    @Column("created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private Instant updatedAt;

    @Column("cancelled_at")
    private Instant cancelledAt;

    @Column("cancellation_reason")
    private String cancellationReason;
}
