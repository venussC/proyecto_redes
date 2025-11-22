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
@Table(schema = "clinic", name = "room_doctor",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"room_id", "doctor_id"}, name = "room_doctor_room_id_doctor_id_unique")
        },
        indexes = {
            @Index(columnList = "room_id", name = "fk_room_doctor_room_idx"),
            @Index(columnList = "doctor_id", name = "fk_room_doctor_doctor_idx")
        }
)
public class RoomDoctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "RoomDoctor's room should not be null")
    @JoinColumn(name = "room_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_room_doctor_room"))
    private Room room;

    @ManyToOne
    @NotNull(message = "RoomDoctor's doctor should not be null")
    @JoinColumn(name = "doctor_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_room_doctor_doctor"))
    private Doctor doctor;

    @NotNull(message = "RoomDoctor's assignedAt should not be null")
    @Column(name = "assigned_at", nullable = false)
    private LocalDateTime assignedAt;

    @PrePersist
    public void prePersist() {
        timestampsInitializer();
    }

    public void timestampsInitializer() {
        if (assignedAt == null) {
            assignedAt = getLocalChronoTime();
        }
    }

    public LocalDateTime getLocalChronoTime() {
        return LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    }
}
