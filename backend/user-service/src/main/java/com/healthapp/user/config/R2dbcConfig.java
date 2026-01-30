package com.healthapp.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

/**
 * R2DBC configuration for reactive database access.
 */
@Configuration
@EnableR2dbcAuditing
public class R2dbcConfig {
    // Additional R2DBC configuration can be added here
}
