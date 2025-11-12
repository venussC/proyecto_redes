package com.clinicturn.api.auth.repository;

import com.clinicturn.api.auth.model.ClinicRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRoleRepository extends JpaRepository<ClinicRole, Long> {

}
