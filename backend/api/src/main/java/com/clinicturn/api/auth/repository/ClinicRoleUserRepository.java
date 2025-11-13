package com.clinicturn.api.auth.repository;

import com.clinicturn.api.auth.model.ClinicRoleUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRoleUserRepository extends JpaRepository<ClinicRoleUser, Long> {

    boolean existsByRole_IdAndUser_Id(Long roleId, Long userId);
}
