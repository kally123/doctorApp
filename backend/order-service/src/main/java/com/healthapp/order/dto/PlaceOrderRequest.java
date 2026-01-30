package com.healthapp.order.dto;

import com.healthapp.order.domain.enums.DeliveryType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

/**
 * Request DTO for placing an order.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderRequest {

    @NotNull(message = "Delivery address is required")
    private UUID deliveryAddressId;

    private DeliveryType deliveryType = DeliveryType.STANDARD;

    private String scheduledDeliverySlot;

    private String notes;

    private String couponCode;

    // Payment info
    private String paymentMethod;

    private UUID prescriptionId;
}
