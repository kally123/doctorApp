package com.healthapp.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Search Service Application.
 * 
 * Provides doctor search and discovery functionality using Elasticsearch.
 */
@SpringBootApplication
@EnableCaching
public class SearchServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(SearchServiceApplication.class, args);
    }
}
