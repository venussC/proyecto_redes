package com.clinicturn.api.patient.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(schema = "patient", name = "turn_status",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "status_name", name = "turn_status_status_name_unique")
        }
)
public class TurnStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "TurnStatus's code should not be null")
    @Column(name = "status_name", nullable = false, length = 10)
    private String name;
}
