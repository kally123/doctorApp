package com.healthapp.order.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Response DTO for order item.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {

    private UUID id;

    private String itemType;

    private String productId;

    private String productName;

    private String productDescription;

    private String manufacturer;

    private String strength;

    private String formulation;

    private Integer packSize;

    private BigDecimal unitPrice;

    private Integer quantity;

    private BigDecimal discountPercent;

    private BigDecimal discountAmount;

    private BigDecimal taxAmount;

    private BigDecimal totalPrice;

    private Boolean requiresPrescription;

    private UUID prescriptionItemId;

    private Boolean isAvailable;

    private String substitutedWith;

    private Integer fulfilledQuantity;
}
