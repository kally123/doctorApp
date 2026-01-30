package com.healthapp.order.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

/**
 * Test category entity for lab test categorization.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("test_categories")
public class TestCategory {

    @Id
    private UUID id;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("icon_url")
    private String iconUrl;

    @Column("display_order")
    private Integer displayOrder;

    @Column("is_active")
    private Boolean isActive;

    @CreatedDate
    @Column("created_at")
    private Instant createdAt;
}
