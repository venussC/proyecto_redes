package com.clinicturn.api.patient.repository;

import com.clinicturn.api.patient.model.Turn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TurnRepository extends JpaRepository<Turn, Long> {

    Optional<Turn> findTopByPatient_IdOrderByCreatedAtDesc(Long patientId);
}