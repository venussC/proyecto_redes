package com.clinicturn.api.auth.repository;

import com.clinicturn.api.auth.model.ClinicRole;
import com.clinicturn.api.auth.model.ClinicRoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClinicRoleRepository extends JpaRepository<ClinicRole, Long> {

    Optional<ClinicRole> findByCode(ClinicRoleType code);

    boolean existsByCode(ClinicRoleType code);
}
