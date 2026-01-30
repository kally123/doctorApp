package com.healthapp.prescription.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Local cache of medicine data from Elasticsearch.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("medicines_cache")
public class MedicineCache {

    @Id
    private UUID id;
    
    @Column("medicine_id")
    private String medicineId;
    
    @Column("brand_name")
    private String brandName;
    
    @Column("generic_name")
    private String genericName;
    
    private String manufacturer;
    
    private String category;
    
    private String formulation;
    
    private String strength;
    
    @Column("pack_size")
    private Integer packSize;
    
    private BigDecimal price;
    
    @Column("requires_prescription")
    @Builder.Default
    private Boolean requiresPrescription = true;
    
    @Column("is_available")
    @Builder.Default
    private Boolean isAvailable = true;
    
    @Column("last_synced_at")
    private Instant lastSyncedAt;
}
