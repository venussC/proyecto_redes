package com.clinicturn.api.auth.repository;

import com.clinicturn.api.auth.model.ClinicUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClinicUserRepository extends JpaRepository<ClinicUser, Long> {

    Optional<ClinicUser> findByUsername(String username);

    boolean existsByUsername(String username);
}
