package com.clinicturn.api.clinic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Builder
@Entity
@Table(schema = "clinic", name = "doctor",
        indexes = {
            @Index(columnList = "speciality_id", name = "fk_doctor_speciality_idx")
        }
)
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Doctor's fullName should not be blank")
    @Column(name = "full_name", nullable = false, length = 300)
    private String fullName;

    @OneToMany
    @NotNull(message = "Doctor's speciality should not be null")
    @JoinColumn(name = "speciality_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_doctor_speciality"))
    private Speciality speciality;

    @NotBlank(message = "Doctor's email should not be blank")
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", length = 10)
    private String phoneNumber;

    @Builder.Default
    @NotNull(message = "Doctor's active should not be blank")
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @NotNull(message = "Doctor's createdAt should not be null")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull(message = "Doctor's updatedAt should not be null")
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
