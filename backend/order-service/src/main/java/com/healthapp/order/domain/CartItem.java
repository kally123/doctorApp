package com.healthapp.order.domain;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Cart item stored within Cart in Redis.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem implements Serializable {

    private String itemId;          // Unique cart item ID

    private String productId;       // Medicine ID

    private String productName;

    private String manufacturer;

    private String strength;

    private String formulation;

    private Integer packSize;

    private BigDecimal unitPrice;

    private BigDecimal mrp;

    private Integer quantity;

    private Boolean requiresPrescription;

    private String prescriptionId;      // If from prescription

    private String prescriptionItemId;

    private String imageUrl;

    public BigDecimal getTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal getSavings() {
        if (mrp == null || mrp.compareTo(unitPrice) <= 0) {
            return BigDecimal.ZERO;
        }
        return mrp.subtract(unitPrice).multiply(BigDecimal.valueOf(quantity));
    }

    public static CartItem fromProduct(String productId, String productName, 
                                        BigDecimal unitPrice, int quantity) {
        return CartItem.builder()
                .itemId(UUID.randomUUID().toString())
                .productId(productId)
                .productName(productName)
                .unitPrice(unitPrice)
                .mrp(unitPrice)
                .quantity(quantity)
                .requiresPrescription(false)
                .build();
    }
}
