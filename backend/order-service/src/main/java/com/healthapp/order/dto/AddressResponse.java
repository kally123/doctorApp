package com.healthapp.order.dto;

import com.healthapp.order.domain.enums.AddressType;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Response DTO for delivery address.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {

    private UUID id;

    private UUID userId;

    private AddressType addressType;

    private String label;

    private String recipientName;

    private String phone;

    private String alternatePhone;

    private String addressLine1;

    private String addressLine2;

    private String landmark;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private Boolean isDefault;

    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        sb.append(addressLine1);
        if (addressLine2 != null && !addressLine2.isBlank()) {
            sb.append(", ").append(addressLine2);
        }
        if (landmark != null && !landmark.isBlank()) {
            sb.append(", Near ").append(landmark);
        }
        sb.append(", ").append(city);
        sb.append(", ").append(state);
        sb.append(" - ").append(postalCode);
        return sb.toString();
    }
}
