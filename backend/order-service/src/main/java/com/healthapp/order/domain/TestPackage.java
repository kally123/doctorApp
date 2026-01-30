package com.healthapp.order.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Test package entity for bundled lab tests.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("test_packages")
public class TestPackage {

    @Id
    private UUID id;

    @Column("package_code")
    private String packageCode;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    // Included tests (array of test IDs)
    @Column("included_tests")
    private UUID[] includedTests;

    // Pricing
    @Column("package_price")
    private BigDecimal packagePrice;

    @Column("individual_price")
    private BigDecimal individualPrice;

    @Column("discount_percent")
    private BigDecimal discountPercent;

    // Details
    @Column("total_parameters")
    private Integer totalParameters;

    @Column("sample_types")
    private String[] sampleTypes;

    @Column("fasting_required")
    private Boolean fastingRequired;

    // Targeting
    @Column("target_gender")
    private String targetGender;

    @Column("target_age_group")
    private String targetAgeGroup;

    @Column("is_popular")
    private Boolean isPopular;

    @Column("display_order")
    private Integer displayOrder;

    @Column("is_active")
    private Boolean isActive;

    @CreatedDate
    @Column("created_at")
    private Instant createdAt;
}
