package com.clinicturn.api.security.service;

import com.clinicturn.api.security.dto.request.RefreshRequest;
import com.clinicturn.api.security.dto.response.RefreshResponse;

public interface RefreshService {

    RefreshResponse refresh(RefreshRequest request);
}
