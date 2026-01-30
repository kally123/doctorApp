package com.healthapp.doctor.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

/**
 * Clinic entity representing a medical facility.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("clinics")
public class Clinic {
    
    @Id
    private UUID id;
    
    private String name;
    
    @Column("address_line1")
    private String addressLine1;
    
    @Column("address_line2")
    private String addressLine2;
    
    private String city;
    
    private String state;
    
    @Builder.Default
    private String country = "India";
    
    @Column("postal_code")
    private String postalCode;
    
    private Double latitude;
    
    private Double longitude;
    
    private String phone;
    
    private String email;
    
    @Column("created_at")
    private Instant createdAt;
    
    @Column("updated_at")
    private Instant updatedAt;
    
    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        if (addressLine1 != null) sb.append(addressLine1);
        if (addressLine2 != null) sb.append(", ").append(addressLine2);
        if (city != null) sb.append(", ").append(city);
        if (state != null) sb.append(", ").append(state);
        if (postalCode != null) sb.append(" - ").append(postalCode);
        return sb.toString();
    }
}
