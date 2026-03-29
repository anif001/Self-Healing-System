package com.selfhealing.ordersystem.repository;

import com.selfhealing.ordersystem.model.IdempotencyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdempotencyRepository extends JpaRepository<IdempotencyRecord, Long> {

    // Check if this key was already used before
    Optional<IdempotencyRecord> findByIdempotencyKey(String idempotencyKey);
}