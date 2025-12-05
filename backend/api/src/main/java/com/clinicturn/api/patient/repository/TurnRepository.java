package com.clinicturn.api.patient.repository;

import com.clinicturn.api.patient.model.Turn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TurnRepository extends JpaRepository<Turn, Long> {

    Optional<Turn> findTopByPatient_IdOrderByCreatedAtDesc(Long patientId);

    @Query(value = """
    SELECT t.turn_number 
    FROM patient.turn t
    ORDER BY 
        substring(t.turn_number from 1 for 1) DESC,
        CAST(substring(t.turn_number from 3) AS INTEGER) DESC
    LIMIT 1
    """, nativeQuery = true)
    Optional<String> findLastTurnNumber();

    @Query("""
    SELECT t 
    FROM Turn t
    WHERE t.status.name NOT IN ('COMPLETED', 'CANCELLED')
    ORDER BY t.createdAt ASC
    """)
    List<Turn> findAllActiveTurns();

}