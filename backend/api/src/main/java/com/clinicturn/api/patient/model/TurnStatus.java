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
            @UniqueConstraint(columnNames = "code", name = "turn_status_code_unique"),
            @UniqueConstraint(columnNames = "display_name", name = "turn_status_display_name_unique")
        }
)
public class TurnStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "TurnStatus's code should not be null")
    @Column(name = "code", nullable = false, length = 4)
    private String code;

    @NotNull(message = "TurnStatus's displayName should not be null")
    @Column(name = "display_name", nullable = false, length = 15)
    private String displayName;
}
