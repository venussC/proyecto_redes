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
@Table(schema = "clinic", name = "clinic",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "clinic_name", name = "clinic_clinic_name_unique")
        }
)
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Clinic's name should not be blank")
    @Column(name = "clinic_name", nullable = false, length = 200)
    private String name;

    @NotBlank(message = "Clinic's address should not be blank")
    @Column(name = "address", nullable = false, length = 350)
    private String address;

    @NotBlank(message = "Clinic's phoneNumber should not be blank")
    @Column(name = "phone_number", nullable = false, length = 10)
    private String phoneNumber;

    @NotNull(message = "Clinic's latitude should not be null")
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @NotNull(message = "Clinic's longitude should not be null")
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @NotNull(message = "Clinic's createdAt should not be null")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull(message = "Clinic's updatedAt should not be null")
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
