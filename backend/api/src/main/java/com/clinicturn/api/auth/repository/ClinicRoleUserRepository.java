package com.clinicturn.api.auth.repository;

import com.clinicturn.api.auth.model.ClinicRoleType;
import com.clinicturn.api.auth.model.ClinicRoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClinicRoleUserRepository extends JpaRepository<ClinicRoleUser, Long> {

    boolean existsByRole_IdAndUser_Id(Long roleId, Long userId);

    @Query("""
           SELECT cru.role.code FROM ClinicRoleUser cru WHERE cru.user.id = :userId
           """)
    List<ClinicRoleType> findRoleCodesByUserId(Long userId);

    Optional<ClinicRoleUser> findFirstByUser_Id(Long userId);

    Optional<ClinicRoleUser> findFirstByUser_Username(String username);
}
