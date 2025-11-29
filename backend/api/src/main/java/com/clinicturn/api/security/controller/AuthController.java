package com.clinicturn.api.security.controller;

import com.clinicturn.api.security.dto.request.LoginRequest;
import com.clinicturn.api.security.dto.request.LogoutRequest;
import com.clinicturn.api.security.dto.request.RefreshRequest;
import com.clinicturn.api.security.dto.request.RegisterRequest;
import com.clinicturn.api.security.dto.response.*;
import com.clinicturn.api.security.service.AuthManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthManagerService authManagerService;

    @Operation(
            summary = "Create a new user",
            description = "Create a new Clinic User with role PATIENT"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully."),
            @ApiResponse(responseCode = "400", description = "One or more fields failed validation."),
            @ApiResponse(responseCode = "409", description = "Resource already exists.")
    })
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = authManagerService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(
            summary = "Login with already existing credentials",
            description = "Login with username + password"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Bad credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authManagerService.login(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Refresh an expired Access Token",
            description = "Get a new Access Token by providing a non-expired Refresh Token"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Refresh successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Bad credentials")
    })
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

    @Operation(
            summary = "Provides user data from authenticated user",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request success")
    })
    @GetMapping("/me")
    public ResponseEntity<MeResponse> me(Authentication authentication) {
        MeResponse response = authManagerService.me(authentication);
        return ResponseEntity.ok(response);
    }
}
