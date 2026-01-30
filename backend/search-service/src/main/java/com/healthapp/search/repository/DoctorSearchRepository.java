package com.healthapp.search.repository;

import com.healthapp.search.model.DoctorDocument;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Reactive Elasticsearch repository for doctor documents.
 */
@Repository
public interface DoctorSearchRepository extends ReactiveElasticsearchRepository<DoctorDocument, String> {
    
    /**
     * Finds doctors by primary specialization.
     */
    Flux<DoctorDocument> findByPrimarySpecialization(String specialization);
    
    /**
     * Finds doctors by city.
     */
    Flux<DoctorDocument> findByCitiesContaining(String city);
    
    /**
     * Checks if a doctor document exists.
     */
    Mono<Boolean> existsById(String id);
    
    /**
     * Finds verified doctors accepting patients.
     */
    Flux<DoctorDocument> findByIsVerifiedTrueAndIsAcceptingPatientsTrue();
}
