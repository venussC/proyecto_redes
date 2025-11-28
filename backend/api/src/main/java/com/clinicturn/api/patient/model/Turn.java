package com.clinicturn.api.patient.model;

import com.clinicturn.api.clinic.model.Doctor;
import com.clinicturn.api.clinic.model.Speciality;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(schema = "patient", name = "turn",
        indexes = {
            @Index(columnList = "speciality_id", name = "fk_turn_speciality_idx"),
            @Index(columnList = "patient_id", name = "fk_turn_patient_idx"),
            @Index(columnList = "doctor_id", name = "fk_turn_doctor_idx"),
            @Index(columnList = "turn_status_id", name = "fk_turn_turn_status_idx")
        }
)
public class Turn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "Turn's speciality should not be null")
    @JoinColumn(name = "speciality_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_turn_speciality"))
    private Speciality speciality;

    @ManyToOne
    @NotNull(message = "Turn's patient should not be null")
    @JoinColumn(name = "patient_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_turn_patient"))
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id",
                foreignKey = @ForeignKey(name = "fk_turn_doctor"))
    private Doctor doctor;

    @NotNull(message = "Turn's reason should not be null")
    @Column(name = "reason", nullable = false, length = 500)
    private String reason;

    @ManyToOne
    @NotNull(message = "Turn's status should not be null")
    @JoinColumn(name = "turn_status_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_turn_turn_status"))
    private TurnStatus status;

    @Column(name = "called_at")
    private LocalDateTime calledAt;

    @Column(name = "seen_at")
    private LocalDateTime seenAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @NotNull(message = "Turn's createdAt should not be null")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull(message = "Turn's updatedAt should not be null")
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
