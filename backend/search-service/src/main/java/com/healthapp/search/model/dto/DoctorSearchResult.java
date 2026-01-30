package com.healthapp.search.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Doctor search result with highlight snippets.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorSearchResult {
    
    private String id;
    private String userId;
    private String fullName;
    private String title;
    private String profilePhotoUrl;
    private String bio;
    
    // Specializations
    private List<SpecializationResult> specializations;
    private String primarySpecialization;
    
    // Qualifications
    private List<QualificationResult> qualifications;
    
    // Languages
    private List<String> languages;
    
    // Location
    private List<ClinicResult> clinics;
    private String primaryCity;
    private Double distance; // Distance from search point (km)
    
    // Professional Info
    private Integer experienceYears;
    
    // Pricing
    private BigDecimal consultationFee;
    private BigDecimal videoConsultationFee;
    
    // Ratings
    private Double rating;
    private Integer totalReviews;
    private Integer totalConsultations;
    
    // Status
    private Boolean isVerified;
    private Boolean isAcceptingPatients;
    private Boolean offersVideoConsultation;
    private Boolean offersInPersonConsultation;
    
    // Search metadata
    private Double score;
    private List<String> highlights;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpecializationResult {
        private String id;
        private String name;
        private Boolean isPrimary;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QualificationResult {
        private String degree;
        private String institution;
        private Integer year;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClinicResult {
        private String id;
        private String name;
        private String address;
        private String city;
        private Boolean isPrimary;
    }
}
