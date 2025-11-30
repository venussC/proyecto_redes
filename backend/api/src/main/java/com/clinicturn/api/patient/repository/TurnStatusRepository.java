package com.clinicturn.api.patient.repository;

import com.clinicturn.api.patient.model.TurnStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TurnStatusRepository extends JpaRepository<TurnStatus, Long> {

    boolean existsByName(String name);

    Optional<TurnStatus> findByName(String name);
}
