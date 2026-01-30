package com.healthapp.doctor.repository;

import com.healthapp.doctor.model.entity.Language;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

/**
 * Reactive repository for Language entity.
 */
@Repository
public interface LanguageRepository extends R2dbcRepository<Language, UUID> {
    
    /**
     * Finds all languages ordered by name.
     */
    Flux<Language> findAllByOrderByNameAsc();
    
    /**
     * Finds languages for a doctor.
     */
    @Query("""
        SELECT l.* FROM languages l
        JOIN doctor_languages dl ON l.id = dl.language_id
        WHERE dl.doctor_id = :doctorId
        ORDER BY l.name ASC
        """)
    Flux<Language> findByDoctorId(UUID doctorId);
}
