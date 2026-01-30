package com.healthapp.common.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * Page request DTO for paginated queries.
 */
@Value
@Builder
public class PageRequest {
    
    @Builder.Default
    int page = 0;
    
    @Builder.Default
    int size = 20;
    
    String sortBy;
    
    @Builder.Default
    SortDirection sortDirection = SortDirection.ASC;
    
    public enum SortDirection {
        ASC, DESC
    }
    
    public int getOffset() {
        return page * size;
    }
    
    public static PageRequest of(int page, int size) {
        return PageRequest.builder()
                .page(page)
                .size(size)
                .build();
    }
    
    public static PageRequest of(int page, int size, String sortBy) {
        return PageRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .build();
    }
}
