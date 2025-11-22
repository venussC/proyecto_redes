package com.clinicturn.api.clinic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Builder
@Entity
@Table(schema = "clinic", name = "room",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"clinic_id","room_number"}, name = "room_clinic_id_room_number_unique")
        },
        indexes = {
            @Index(columnList = "clinic_id", name = "fk_room_clinic_idx")
        }
)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "Room's clinic should not be null")
    @JoinColumn(name = "clinic_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_room_clinic"))
    private Clinic clinic;

    @NotNull(message = "Room's roomNumber should not be null")
    @Column(name = "room_number", nullable = false)
    private Integer roomNumber;

    @Builder.Default
    @NotNull(message = "Room's active should not be null")
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Builder.Default
    @NotNull(message = "Room's available should not be null")
    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = true;

    @NotNull(message = "Room's createdAt should not be null")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull(message = "Room's updatedAt should not be null")
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
