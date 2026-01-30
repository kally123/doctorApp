package com.healthapp.search.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Search response with facets and aggregations.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorSearchResponse {
    
    /**
     * Search results.
     */
    private List<DoctorSearchResult> results;
    
    /**
     * Total matching documents.
     */
    private Long totalHits;
    
    /**
     * Current page (0-based).
     */
    private Integer page;
    
    /**
     * Page size.
     */
    private Integer size;
    
    /**
     * Total pages.
     */
    private Integer totalPages;
    
    /**
     * Search took time in milliseconds.
     */
    private Long tookMs;
    
    /**
     * Aggregations/facets for filtering.
     */
    private Aggregations aggregations;
    
    /**
     * Suggestions for search query.
     */
    private List<String> suggestions;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Aggregations {
        /**
         * Specialization counts.
         */
        private List<FacetBucket> specializations;
        
        /**
         * City counts.
         */
        private List<FacetBucket> cities;
        
        /**
         * Language counts.
         */
        private List<FacetBucket> languages;
        
        /**
         * Experience range counts.
         */
        private List<RangeBucket> experienceRanges;
        
        /**
         * Fee range counts.
         */
        private List<RangeBucket> feeRanges;
        
        /**
         * Rating distribution.
         */
        private List<FacetBucket> ratings;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FacetBucket {
        private String key;
        private Long count;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RangeBucket {
        private String key;
        private Double from;
        private Double to;
        private Long count;
    }
}
