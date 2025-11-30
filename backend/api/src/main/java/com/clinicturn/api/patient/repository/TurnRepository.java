package com.clinicturn.api.patient.repository;

import com.clinicturn.api.patient.model.Turn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TurnRepository extends JpaRepository<Turn, Long> {

    Optional<Turn> findTopByPatient_IdOrderByCreatedAtDesc(Long patientId);

    @Query("SELECT t.number FROM Turn t ORDER BY t.id DESC")
    Optional<String> findLastTurnNumber();
}