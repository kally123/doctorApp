package com.healthapp.ehr.repository;

import com.healthapp.ehr.domain.HealthRecord;
import com.healthapp.ehr.domain.enums.RecordType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Repository for health records.
 */
public interface HealthRecordRepository extends ReactiveMongoRepository<HealthRecord, String> {

    Flux<HealthRecord> findByPatientIdOrderByRecordDateDesc(UUID patientId);

    Flux<HealthRecord> findByPatientIdAndRecordTypeOrderByRecordDateDesc(UUID patientId, RecordType recordType);

    Flux<HealthRecord> findByPatientIdAndRecordTypeInOrderByRecordDateDesc(UUID patientId, List<RecordType> recordTypes);

    Flux<HealthRecord> findByPatientIdAndRecordDateBetweenOrderByRecordDateDesc(
            UUID patientId, LocalDateTime startDate, LocalDateTime endDate);

    Flux<HealthRecord> findByConsultationId(UUID consultationId);

    Flux<HealthRecord> findByDoctorIdOrderByRecordDateDesc(UUID doctorId);

    @Query("{'patientId': ?0, 'tags': {$in: ?1}}")
    Flux<HealthRecord> findByPatientIdAndTagsContaining(UUID patientId, List<String> tags);

    @Query("{'patientId': ?0, 'isArchived': false, 'isConfidential': false}")
    Flux<HealthRecord> findShareableRecords(UUID patientId);

    @Query("{'patientId': ?0, '$text': {'$search': ?1}}")
    Flux<HealthRecord> searchByText(UUID patientId, String searchText);

    Mono<Long> countByPatientIdAndRecordType(UUID patientId, RecordType recordType);
}
