package com.healthapp.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

/**
 * Request DTO for adding item to cart.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartRequest {

    @NotBlank(message = "Product ID is required")
    private String productId;

    @NotBlank(message = "Product name is required")
    private String productName;

    private String manufacturer;

    private String strength;

    private String formulation;

    private Integer packSize;

    private BigDecimal unitPrice;

    private BigDecimal mrp;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity = 1;

    private Boolean requiresPrescription;

    private String prescriptionId;

    private String prescriptionItemId;

    private String imageUrl;
}
