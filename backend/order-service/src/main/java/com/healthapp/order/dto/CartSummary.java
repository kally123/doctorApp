package com.healthapp.order.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Cart summary with delivery and pricing details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartSummary {

    private List<CartItemResponse> items;

    private int itemCount;

    private int totalItems;

    private BigDecimal subtotal;

    private String couponCode;

    private BigDecimal discount;

    private BigDecimal deliveryFee;

    private BigDecimal tax;

    private BigDecimal total;

    private AddressResponse deliveryAddress;

    private Instant estimatedDelivery;

    private boolean freeDeliveryEligible;

    private BigDecimal amountForFreeDelivery;
}
