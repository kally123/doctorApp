package com.healthapp.common.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * Page response DTO for paginated results.
 */
@Value
@Builder
public class PageResponse<T> {
    
    List<T> content;
    int page;
    int size;
    long totalElements;
    int totalPages;
    boolean first;
    boolean last;
    boolean hasNext;
    boolean hasPrevious;
    
    public static <T> PageResponse<T> of(List<T> content, PageRequest pageRequest, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / pageRequest.getSize());
        int currentPage = pageRequest.getPage();
        
        return PageResponse.<T>builder()
                .content(content)
                .page(currentPage)
                .size(pageRequest.getSize())
                .totalElements(totalElements)
                .totalPages(totalPages)
                .first(currentPage == 0)
                .last(currentPage >= totalPages - 1)
                .hasNext(currentPage < totalPages - 1)
                .hasPrevious(currentPage > 0)
                .build();
    }
    
    public static <T> PageResponse<T> empty(PageRequest pageRequest) {
        return PageResponse.<T>builder()
                .content(List.of())
                .page(pageRequest.getPage())
                .size(pageRequest.getSize())
                .totalElements(0)
                .totalPages(0)
                .first(true)
                .last(true)
                .hasNext(false)
                .hasPrevious(false)
                .build();
    }
}
