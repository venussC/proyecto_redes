package com.clinicturn.api.auth.repository;

import com.clinicturn.api.auth.model.ClinicUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicUserRepository extends JpaRepository<ClinicUser, Long> {

}
