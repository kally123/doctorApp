package com.healthapp.doctor.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

/**
 * Doctor qualification entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("doctor_qualifications")
public class DoctorQualification {
    
    @Id
    private UUID id;
    
    @Column("doctor_id")
    private UUID doctorId;
    
    private String degree;
    
    private String institution;
    
    @Column("year_of_completion")
    private Integer yearOfCompletion;
    
    @Column("certificate_url")
    private String certificateUrl;
    
    @Column("is_verified")
    @Builder.Default
    private Boolean isVerified = false;
    
    @Column("created_at")
    private Instant createdAt;
}
