package com.clinicturn.api.patient.repository;

import com.clinicturn.api.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    boolean existsByUser_Id(Long userId);

    Optional<Patient> findByUser_Id(Long userId);

    Optional<Patient> findByUser_Username(String username);
}
