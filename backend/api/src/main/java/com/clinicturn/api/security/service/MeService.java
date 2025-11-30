package com.clinicturn.api.security.service;

import com.clinicturn.api.security.dto.response.MeResponse;
import org.springframework.security.core.Authentication;

public interface MeService {

    MeResponse me(Authentication authentication);
}
