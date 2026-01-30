package com.healthapp.order.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Response DTO for cart.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {

    private String userId;

    private List<CartItemResponse> items;

    private int itemCount;

    private int totalItems;

    private BigDecimal subtotal;

    private String couponCode;

    private BigDecimal discountAmount;

    private Instant updatedAt;
}
