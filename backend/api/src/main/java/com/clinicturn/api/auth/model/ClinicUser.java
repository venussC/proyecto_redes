package com.clinicturn.api.auth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(schema = "auth", name = "clinic_user",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "username", name = "user_username_unique")
        }
)
public class ClinicUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "ClinicUser's username should not be blank")
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @NotBlank(message = "ClinicUser's passwordHash should not be blank")
    @Column(name = "password_hash", nullable = false, length = 150)
    private String passwordHash;

    @NotBlank(message = "ClinicUser's phoneNumber should not be blank")
    @Column(name = "phone_number", nullable = false, length = 9)
    private String phoneNumber;

    @Builder.Default
    @NotNull(message = "ClinicUser's accountNonLocked should not be null")
    @Column(name = "account_non_locked", nullable = false)
    private Boolean accountNonLocked = true;

    @Builder.Default
    @NotNull(message = "ClinicUser's enabled should not be null")
    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @Builder.Default
    @Column(name = "login_attempts")
    private Integer loginAttempts = 0;

    @Column(name = "recuperation_token", length = 6)
    private String recuperationToken;

    @Column(name = "recuperation_token_exp_at")
    private LocalDateTime recuperationTokenExpAt;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @NotNull(message = "ClinicUser's createdAt should not be null")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull(message = "ClinicUser's updatedAt should not be null")
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        timestampsInitializer();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = getLocalChronoTime();
    }

    public void timestampsInitializer() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
        if (createdAt == null) {
            createdAt = now;
        }
        if (updatedAt == null) {
            updatedAt = now;
        }
    }

    public LocalDateTime getLocalChronoTime() {
        return LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    }
}
