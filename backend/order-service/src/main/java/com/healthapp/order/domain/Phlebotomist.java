package com.healthapp.order.domain;

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
 * Phlebotomist entity for home collection staff.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("phlebotomists")
public class Phlebotomist {

    @Id
    private UUID id;

    @Column("lab_partner_id")
    private UUID labPartnerId;

    @Column("name")
    private String name;

    @Column("phone")
    private String phone;

    @Column("email")
    private String email;

    @Column("certification_number")
    private String certificationNumber;

    @Column("experience_years")
    private Integer experienceYears;

    @Column("current_latitude")
    private BigDecimal currentLatitude;

    @Column("current_longitude")
    private BigDecimal currentLongitude;

    @Column("last_location_update")
    private Instant lastLocationUpdate;

    @Column("is_active")
    private Boolean isActive;

    @CreatedDate
    @Column("created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private Instant updatedAt;
}
