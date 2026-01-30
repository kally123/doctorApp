package com.healthapp.order.dto;

import jakarta.validation.constraints.Min;
import lombok.*;

/**
 * Request DTO for updating cart item quantity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartItemRequest {

    @Min(value = 0, message = "Quantity must be non-negative")
    private int quantity;
}
