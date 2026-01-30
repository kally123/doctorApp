package com.healthapp.order.domain;

import com.healthapp.order.domain.enums.AddressType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Delivery address entity for users.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("delivery_addresses")
public class DeliveryAddress {

    @Id
    private UUID id;

    @Column("user_id")
    private UUID userId;

    @Column("address_type")
    private AddressType addressType;

    @Column("label")
    private String label;

    @Column("recipient_name")
    private String recipientName;

    @Column("phone")
    private String phone;

    @Column("alternate_phone")
    private String alternatePhone;

    @Column("address_line1")
    private String addressLine1;

    @Column("address_line2")
    private String addressLine2;

    @Column("landmark")
    private String landmark;

    @Column("city")
    private String city;

    @Column("state")
    private String state;

    @Column("postal_code")
    private String postalCode;

    @Column("country")
    private String country;

    @Column("latitude")
    private BigDecimal latitude;

    @Column("longitude")
    private BigDecimal longitude;

    @Column("is_default")
    private Boolean isDefault;

    @Column("is_active")
    private Boolean isActive;

    @CreatedDate
    @Column("created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private Instant updatedAt;
}
