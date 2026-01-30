package com.healthapp.doctor.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

/**
 * Specialization entity for medical specialties.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("specializations")
public class Specialization {
    
    @Id
    private UUID id;
    
    private String name;
    
    @Column("parent_specialty_id")
    private UUID parentSpecialtyId;
    
    private String description;
    
    @Column("icon_url")
    private String iconUrl;
    
    @Column("is_active")
    @Builder.Default
    private Boolean isActive = true;
    
    @Column("display_order")
    @Builder.Default
    private Integer displayOrder = 0;
    
    @Column("created_at")
    private Instant createdAt;
}
