package com.healthapp.order.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Order item entity - individual items in an order.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("order_items")
public class OrderItem {

    @Id
    private UUID id;

    @Column("order_id")
    private UUID orderId;

    @Column("item_type")
    private String itemType; // MEDICINE, LAB_TEST

    // Product info (snapshot at order time)
    @Column("product_id")
    private String productId;

    @Column("product_name")
    private String productName;

    @Column("product_description")
    private String productDescription;

    @Column("manufacturer")
    private String manufacturer;

    // For medicines
    @Column("strength")
    private String strength;

    @Column("formulation")
    private String formulation;

    @Column("pack_size")
    private Integer packSize;

    // Pricing
    @Column("unit_price")
    private BigDecimal unitPrice;

    @Column("quantity")
    private Integer quantity;

    @Column("discount_percent")
    private BigDecimal discountPercent;

    @Column("discount_amount")
    private BigDecimal discountAmount;

    @Column("tax_percent")
    private BigDecimal taxPercent;

    @Column("tax_amount")
    private BigDecimal taxAmount;

    @Column("total_price")
    private BigDecimal totalPrice;

    // Prescription reference
    @Column("prescription_item_id")
    private UUID prescriptionItemId;

    @Column("requires_prescription")
    private Boolean requiresPrescription;

    // Fulfillment
    @Column("is_available")
    private Boolean isAvailable;

    @Column("substituted_with")
    private String substitutedWith;

    @Column("fulfilled_quantity")
    private Integer fulfilledQuantity;

    @CreatedDate
    @Column("created_at")
    private Instant createdAt;
}
