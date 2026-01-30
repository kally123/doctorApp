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
 * Lab test entity for the test catalog.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("lab_tests")
public class LabTest {

    @Id
    private UUID id;

    @Column("test_code")
    private String testCode;

    @Column("name")
    private String name;

    @Column("short_name")
    private String shortName;

    @Column("description")
    private String description;

    @Column("category_id")
    private UUID categoryId;

    // Test details
    @Column("sample_type")
    private String sampleType;

    @Column("sample_volume")
    private String sampleVolume;

    @Column("fasting_required")
    private Boolean fastingRequired;

    @Column("fasting_hours")
    private Integer fastingHours;

    @Column("preparation_instructions")
    private String preparationInstructions;

    // Reporting
    @Column("report_available_in")
    private String reportAvailableIn;

    @Column("report_format")
    private String reportFormat;

    // Parameters (JSON)
    @Column("parameters")
    private String parameters;

    // Pricing
    @Column("base_price")
    private BigDecimal basePrice;

    @Column("mrp")
    private BigDecimal mrp;

    // Flags
    @Column("is_popular")
    private Boolean isPopular;

    @Column("requires_doctor_referral")
    private Boolean requiresDoctorReferral;

    @Column("home_collection_available")
    private Boolean homeCollectionAvailable;

    @Column("keywords")
    private String[] keywords;

    @Column("is_active")
    private Boolean isActive;

    @CreatedDate
    @Column("created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private Instant updatedAt;
}
