package com.healthapp.order.domain;

import com.healthapp.order.domain.enums.PartnerType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Partner entity for pharmacies and labs.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("partners")
public class Partner {

    @Id
    private UUID id;

    @Column("partner_type")
    private PartnerType partnerType;

    // Business info
    @Column("business_name")
    private String businessName;

    @Column("legal_name")
    private String legalName;

    @Column("registration_number")
    private String registrationNumber;

    @Column("license_number")
    private String licenseNumber;

    @Column("license_expiry")
    private LocalDate licenseExpiry;

    // Contact
    @Column("email")
    private String email;

    @Column("phone")
    private String phone;

    // Address
    @Column("address_line1")
    private String addressLine1;

    @Column("address_line2")
    private String addressLine2;

    @Column("city")
    private String city;

    @Column("state")
    private String state;

    @Column("postal_code")
    private String postalCode;

    @Column("latitude")
    private BigDecimal latitude;

    @Column("longitude")
    private BigDecimal longitude;

    // Operating hours (JSON)
    @Column("operating_hours")
    private String operatingHours;

    // Service area
    @Column("service_radius_km")
    private Integer serviceRadiusKm;

    @Column("serviceable_pincodes")
    private String[] serviceablePincodes;

    // API integration
    @Column("api_key_hash")
    private String apiKeyHash;

    @Column("webhook_url")
    private String webhookUrl;

    @Column("is_api_enabled")
    private Boolean isApiEnabled;

    // Status
    @Column("is_active")
    private Boolean isActive;

    @Column("is_verified")
    private Boolean isVerified;

    @Column("verified_at")
    private Instant verifiedAt;

    // Ratings
    @Column("rating")
    private BigDecimal rating;

    @Column("total_orders")
    private Integer totalOrders;

    // Commission
    @Column("commission_percent")
    private BigDecimal commissionPercent;

    @CreatedDate
    @Column("created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private Instant updatedAt;
}
