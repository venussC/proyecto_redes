package com.clinicturn.api.clinic.repository;

import com.clinicturn.api.clinic.model.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialityRepository extends JpaRepository<Speciality, Long> {

    boolean existsByCode(String code);

    boolean existsByDisplayName(String displayName);
}
