package com.clinicturn.api.patient.repository;

import com.clinicturn.api.patient.model.Turn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurnRepository extends JpaRepository<Turn, Long> {
}
