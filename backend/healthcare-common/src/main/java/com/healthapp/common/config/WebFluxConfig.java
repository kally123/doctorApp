package com.healthapp.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * WebFlux configuration.
 */
@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {
    
    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        // Configure max memory size for request/response bodies (default 256KB)
        configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024); // 10MB
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
