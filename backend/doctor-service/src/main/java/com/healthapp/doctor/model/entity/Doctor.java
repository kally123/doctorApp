package com.healthapp.doctor.model.entity;

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
 * Doctor entity representing a doctor profile.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("doctors")
public class Doctor {
    
    @Id
    private UUID id;
    
    @Column("user_id")
    private UUID userId;
    
    @Column("full_name")
    private String fullName;
    
    @Column("profile_picture_url")
    private String profilePictureUrl;
    
    @Column("registration_number")
    private String registrationNumber;
    
    @Column("registration_council")
    private String registrationCouncil;
    
    @Column("experience_years")
    private Integer experienceYears;
    
    private String bio;
    
    @Column("consultation_fee")
    private BigDecimal consultationFee;
    
    @Column("video_consultation_fee")
    private BigDecimal videoConsultationFee;
    
    @Column("followup_fee")
    private BigDecimal followupFee;
    
    @Builder.Default
    private BigDecimal rating = BigDecimal.ZERO;
    
    @Column("review_count")
    @Builder.Default
    private Integer reviewCount = 0;
    
    @Column("is_verified")
    @Builder.Default
    private Boolean isVerified = false;
    
    @Column("is_accepting_patients")
    @Builder.Default
    private Boolean isAcceptingPatients = true;
    
    @Column("verification_date")
    private Instant verificationDate;
    
    @Column("profile_completeness")
    @Builder.Default
    private Integer profileCompleteness = 0;
    
    @CreatedDate
    @Column("created_at")
    private Instant createdAt;
    
    @LastModifiedDate
    @Column("updated_at")
    private Instant updatedAt;
}
