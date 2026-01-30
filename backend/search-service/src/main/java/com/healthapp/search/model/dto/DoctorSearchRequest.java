package com.healthapp.search.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Search request with filters and sorting options.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorSearchRequest {
    
    /**
     * Free text search query.
     */
    private String query;
    
    /**
     * Filter by specialization IDs.
     */
    private List<String> specializationIds;
    
    /**
     * Filter by specialization name.
     */
    private String specialization;
    
    /**
     * Filter by city.
     */
    private String city;
    
    /**
     * Filter by state.
     */
    private String state;
    
    /**
     * Filter by pincode.
     */
    private String pincode;
    
    /**
     * Geo search - latitude.
     */
    private Double latitude;
    
    /**
     * Geo search - longitude.
     */
    private Double longitude;
    
    /**
     * Geo search radius in kilometers.
     */
    @Min(1)
    @Max(100)
    private Integer radiusKm;
    
    /**
     * Minimum rating filter.
     */
    @Min(0)
    @Max(5)
    private Double minRating;
    
    /**
     * Maximum consultation fee.
     */
    private BigDecimal maxFee;
    
    /**
     * Minimum consultation fee.
     */
    private BigDecimal minFee;
    
    /**
     * Minimum years of experience.
     */
    @Min(0)
    private Integer minExperience;
    
    /**
     * Filter by languages spoken.
     */
    private List<String> languages;
    
    /**
     * Filter by verified doctors only.
     */
    private Boolean verifiedOnly;
    
    /**
     * Filter by accepting patients only.
     */
    @Builder.Default
    private Boolean acceptingPatientsOnly = true;
    
    /**
     * Filter by video consultation availability.
     */
    private Boolean offersVideoConsultation;
    
    /**
     * Filter by in-person consultation availability.
     */
    private Boolean offersInPersonConsultation;
    
    /**
     * Sort field.
     */
    @Builder.Default
    private SortField sortBy = SortField.RELEVANCE;
    
    /**
     * Sort direction.
     */
    @Builder.Default
    private SortDirection sortDirection = SortDirection.DESC;
    
    /**
     * Page number (0-based).
     */
    @Min(0)
    @Builder.Default
    private Integer page = 0;
    
    /**
     * Page size.
     */
    @Min(1)
    @Max(100)
    @Builder.Default
    private Integer size = 20;
    
    public enum SortField {
        RELEVANCE,
        RATING,
        EXPERIENCE,
        CONSULTATION_FEE,
        POPULARITY,
        DISTANCE
    }
    
    public enum SortDirection {
        ASC,
        DESC
    }
}
