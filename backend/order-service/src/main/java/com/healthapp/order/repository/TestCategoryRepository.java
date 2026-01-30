package com.healthapp.order.repository;

import com.healthapp.order.domain.TestCategory;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

/**
 * Repository for TestCategory entity.
 */
@Repository
public interface TestCategoryRepository extends R2dbcRepository<TestCategory, UUID> {

    Flux<TestCategory> findByIsActiveTrueOrderByDisplayOrderAsc();
}
