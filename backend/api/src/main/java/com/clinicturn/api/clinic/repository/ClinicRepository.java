package com.clinicturn.api.clinic.repository;

import com.clinicturn.api.clinic.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {

    Boolean existsByName(String name);
}
