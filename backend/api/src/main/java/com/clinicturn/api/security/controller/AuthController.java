package com.clinicturn.api.security.controller;

import com.clinicturn.api.security.dto.request.LoginRequest;
import com.clinicturn.api.security.dto.request.LogoutRequest;
import com.clinicturn.api.security.dto.request.RefreshRequest;
import com.clinicturn.api.security.dto.response.LoginResponse;
import com.clinicturn.api.security.dto.response.LogoutResponse;
import com.clinicturn.api.security.dto.response.RefreshResponse;
import com.clinicturn.api.security.service.AuthManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthManagerService authManagerService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authManagerService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refresh(@Valid @RequestBody RefreshRequest request) {
        RefreshResponse response = authManagerService.refresh(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(@Valid @RequestBody LogoutRequest request) {
        LogoutResponse response = authManagerService.logout(request);
        return ResponseEntity.ok(response);
    }
}
