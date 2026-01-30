package com.healthapp.search.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Elasticsearch document for doctor search.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "doctors")
@Setting(settingPath = "elasticsearch/doctor-settings.json")
@Mapping(mappingPath = "elasticsearch/doctor-mapping.json")
public class DoctorDocument {
    
    @Id
    private String id;
    
    @Field(type = FieldType.Keyword)
    private String userId;
    
    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "standard"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword),
                    @InnerField(suffix = "autocomplete", type = FieldType.Text, analyzer = "autocomplete")
            }
    )
    private String fullName;
    
    @Field(type = FieldType.Text)
    private String firstName;
    
    @Field(type = FieldType.Text)
    private String lastName;
    
    @Field(type = FieldType.Keyword)
    private String title;
    
    @Field(type = FieldType.Text, analyzer = "standard")
    private String bio;
    
    @Field(type = FieldType.Keyword)
    private String profilePhotoUrl;
    
    // Specializations
    @Field(type = FieldType.Nested)
    private List<SpecializationInfo> specializations;
    
    @Field(type = FieldType.Keyword)
    private String primarySpecialization;
    
    @Field(type = FieldType.Keyword)
    private String primarySpecializationId;
    
    // Qualifications
    @Field(type = FieldType.Nested)
    private List<QualificationInfo> qualifications;
    
    // Languages
    @Field(type = FieldType.Keyword)
    private List<String> languages;
    
    // Location
    @Field(type = FieldType.Nested)
    private List<ClinicInfo> clinics;
    
    @Field(type = FieldType.Keyword)
    private List<String> cities;
    
    @GeoPointField
    private GeoLocation primaryLocation;
    
    // Professional Info
    @Field(type = FieldType.Integer)
    private Integer experienceYears;
    
    @Field(type = FieldType.Keyword)
    private String registrationNumber;
    
    // Pricing
    @Field(type = FieldType.Double)
    private BigDecimal consultationFee;
    
    @Field(type = FieldType.Double)
    private BigDecimal videoConsultationFee;
    
    // Ratings
    @Field(type = FieldType.Double)
    private Double rating;
    
    @Field(type = FieldType.Integer)
    private Integer totalReviews;
    
    @Field(type = FieldType.Integer)
    private Integer totalConsultations;
    
    @Field(type = FieldType.Integer)
    private Integer profileViews;
    
    // Status
    @Field(type = FieldType.Boolean)
    private Boolean isVerified;
    
    @Field(type = FieldType.Boolean)
    private Boolean isAcceptingPatients;
    
    @Field(type = FieldType.Boolean)
    private Boolean offersVideoConsultation;
    
    @Field(type = FieldType.Boolean)
    private Boolean offersInPersonConsultation;
    
    // Timestamps
    @Field(type = FieldType.Date, format = DateFormat.epoch_millis)
    private Instant createdAt;
    
    @Field(type = FieldType.Date, format = DateFormat.epoch_millis)
    private Instant updatedAt;
    
    // Computed popularity score for ranking
    @Field(type = FieldType.Double)
    private Double popularityScore;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpecializationInfo {
        @Field(type = FieldType.Keyword)
        private String id;
        
        @MultiField(
                mainField = @Field(type = FieldType.Text, analyzer = "standard"),
                otherFields = @InnerField(suffix = "keyword", type = FieldType.Keyword)
        )
        private String name;
        
        @Field(type = FieldType.Boolean)
        private Boolean isPrimary;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QualificationInfo {
        @Field(type = FieldType.Keyword)
        private String degree;
        
        @Field(type = FieldType.Text)
        private String institution;
        
        @Field(type = FieldType.Integer)
        private Integer year;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClinicInfo {
        @Field(type = FieldType.Keyword)
        private String id;
        
        @Field(type = FieldType.Text)
        private String name;
        
        @Field(type = FieldType.Text)
        private String address;
        
        @Field(type = FieldType.Keyword)
        private String city;
        
        @Field(type = FieldType.Keyword)
        private String state;
        
        @Field(type = FieldType.Keyword)
        private String pincode;
        
        @GeoPointField
        private GeoLocation location;
        
        @Field(type = FieldType.Boolean)
        private Boolean isPrimary;
        
        @Field(type = FieldType.Double)
        private BigDecimal consultationFee;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeoLocation {
        private Double lat;
        private Double lon;
    }
}
