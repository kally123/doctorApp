package com.healthapp.order.dto;

import lombok.*;

import java.math.BigDecimal;

/**
 * Response DTO for cart item.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {

    private String itemId;

    private String productId;

    private String productName;

    private String manufacturer;

    private String strength;

    private String formulation;

    private Integer packSize;

    private BigDecimal unitPrice;

    private BigDecimal mrp;

    private Integer quantity;

    private BigDecimal total;

    private BigDecimal savings;

    private Boolean requiresPrescription;

    private String prescriptionId;

    private String imageUrl;
}
