package com.healthapp.user.repository;

import com.healthapp.user.model.entity.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Reactive repository for User entity.
 */
@Repository
public interface UserRepository extends R2dbcRepository<User, UUID> {
    
    /**
     * Finds a user by email address.
     */
    Mono<User> findByEmail(String email);
    
    /**
     * Finds a user by phone number.
     */
    Mono<User> findByPhone(String phone);
    
    /**
     * Finds a user by email or phone.
     */
    @Query("SELECT * FROM users WHERE email = :identifier OR phone = :identifier")
    Mono<User> findByEmailOrPhone(String identifier);
    
    /**
     * Finds a user by Google ID.
     */
    Mono<User> findByGoogleId(String googleId);
    
    /**
     * Finds a user by Apple ID.
     */
    Mono<User> findByAppleId(String appleId);
    
    /**
     * Checks if an email exists.
     */
    Mono<Boolean> existsByEmail(String email);
    
    /**
     * Checks if a phone number exists.
     */
    Mono<Boolean> existsByPhone(String phone);
}
