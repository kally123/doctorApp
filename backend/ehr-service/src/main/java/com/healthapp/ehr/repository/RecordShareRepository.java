package com.healthapp.ehr.repository;

import com.healthapp.ehr.domain.RecordShare;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Repository for record sharing permissions.
 */
public interface RecordShareRepository extends ReactiveMongoRepository<RecordShare, String> {

    Flux<RecordShare> findByPatientIdAndIsActiveTrue(UUID patientId);

    Flux<RecordShare> findBySharedWithIdAndIsActiveTrue(UUID sharedWithId);

    Mono<RecordShare> findByPatientIdAndSharedWithIdAndIsActiveTrue(UUID patientId, UUID sharedWithId);

    @Query("{'patientId': ?0, 'sharedWithId': ?1, 'isActive': true, 'validFrom': {$lte: ?2}, 'validUntil': {$gte: ?2}}")
    Mono<RecordShare> findActiveShare(UUID patientId, UUID sharedWithId, LocalDateTime now);

    Flux<RecordShare> findByPatientIdAndIsRevokedTrue(UUID patientId);

    @Query("{'validUntil': {$lt: ?0}, 'isActive': true}")
    Flux<RecordShare> findExpiredShares(LocalDateTime now);

    Mono<Long> countByPatientIdAndIsActiveTrue(UUID patientId);
}
