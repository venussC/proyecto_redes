package com.clinicturn.api.auth.repository;

import com.clinicturn.api.auth.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

}
