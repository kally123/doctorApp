package com.healthapp.prescription.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

/**
 * Prescription template for reusable prescriptions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("prescription_templates")
public class PrescriptionTemplate {

    @Id
    private UUID id;
    
    @Column("doctor_id")
    private UUID doctorId;
    
    @Column("template_name")
    private String templateName;
    
    private String description;
    
    // Template conditions
    private String diagnosis;
    
    private String specialization;
    
    // Template content (JSON)
    @Column("template_items")
    private String templateItems;
    
    @Column("general_advice")
    private String generalAdvice;
    
    @Column("diet_advice")
    private String dietAdvice;
    
    // Usage tracking
    @Column("usage_count")
    @Builder.Default
    private Integer usageCount = 0;
    
    @Column("last_used_at")
    private Instant lastUsedAt;
    
    // Status
    @Column("is_active")
    @Builder.Default
    private Boolean isActive = true;
    
    @Column("is_public")
    @Builder.Default
    private Boolean isPublic = false;
    
    @CreatedDate
    @Column("created_at")
    private Instant createdAt;
    
    @LastModifiedDate
    @Column("updated_at")
    private Instant updatedAt;
}
