package com.clinicturn.api.clinic.repository;

import com.clinicturn.api.clinic.model.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecialityRepository extends JpaRepository<Speciality, Long> {

    Optional<Speciality> findByCode(String code);

    boolean existsByCode(String code);

    boolean existsByDisplayName(String displayName);
}
