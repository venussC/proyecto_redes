package com.clinicturn.api.patient.model;

import com.clinicturn.api.auth.model.ClinicUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "patient", name = "patient",
        indexes = {
            @Index(columnList = "user_id", name = "fk_patient_clinic_user_idx")
        })
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "User should not be null")
    @JoinColumn(name = "user_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_patient_clinic_user"))
    private ClinicUser user;

    @NotBlank(message = "fullName should not be blank")
    @Column(name = "full_name", nullable = false, length = 300)
    private String fullName;

    @NotBlank(message = "fullName should not be blank")
    @Column(name = "dni", nullable = false, length = 20)
    private String dni;

    @NotBlank(message = "phoneNumber should not be blank")
    @Column(name = "phone_number", nullable = false, length = 9)
    private String phoneNumber;

    @NotNull(message = "createdAt should not be null")
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
}
