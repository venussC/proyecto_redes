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
@Table(schema = "auth", name = "refresh_token",
        indexes = {
            @Index(columnList = "user_id", name = "fk_refresh_token_clinic_user_idx")
        }
)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "RefreshToken's user should not be null")
    @JoinColumn(name = "user_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_refresh_token_clinic_user")
    )
    private ClinicUser user;

    @NotBlank(message = "RefreshToken's tokenHash should not be blank")
    @Column(name = "token_hash", nullable = false, length = 64)
    private String tokenHash;

    @Builder.Default
    @NotNull(message = "RefreshToken's isRevoked should not be null")
    @Column(name = "is_revoked", nullable = false)
    private Boolean isRevoked = false;

    @NotNull(message = "RefreshToken's expiresAt should not be null")
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @NotNull(message = "RefreshToken's createdAt should not be null")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        timestampsInitializer();
    }

    public void timestampsInitializer() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
        if (createdAt == null) {
            createdAt = now;
        }
    }

    public LocalDateTime getLocalChronoTime() {
        return LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    }
}
