package com.clinicturn.api.security.controller;

import com.clinicturn.api.security.dto.request.LoginRequest;
import com.clinicturn.api.security.dto.response.LoginResponse;
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
}
