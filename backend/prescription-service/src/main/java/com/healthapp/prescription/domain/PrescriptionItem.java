package com.healthapp.prescription.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

/**
 * Prescription item - individual medicine in a prescription.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("prescription_items")
public class PrescriptionItem {

    @Id
    private UUID id;
    
    @Column("prescription_id")
    private UUID prescriptionId;
    
    // Medicine reference
    @Column("medicine_id")
    private String medicineId;
    
    @Column("medicine_name")
    private String medicineName;
    
    @Column("generic_name")
    private String genericName;
    
    private String manufacturer;
    
    // Dosage information
    private String strength;
    
    private String formulation;
    
    // Prescription details
    private String dosage;
    
    private String frequency;
    
    private String duration;
    
    private String timing;
    
    private String route;
    
    private Integer quantity;
    
    @Column("quantity_unit")
    private String quantityUnit;
    
    // Additional instructions
    @Column("special_instructions")
    private String specialInstructions;
    
    // Ordering
    @Column("sequence_order")
    @Builder.Default
    private Integer sequenceOrder = 0;
    
    // Dispensing tracking
    @Column("is_dispensed")
    @Builder.Default
    private Boolean isDispensed = false;
    
    @Column("dispensed_quantity")
    @Builder.Default
    private Integer dispensedQuantity = 0;
    
    @CreatedDate
    @Column("created_at")
    private Instant createdAt;
}
