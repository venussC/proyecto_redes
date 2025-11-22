package com.clinicturn.api.clinic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(schema = "clinic", name = "speciality",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "code", name = "speciality_code_unique"),
            @UniqueConstraint(columnNames = "display_name", name = "speciality_display_name_unique")
        }
)
public class Speciality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Speciality's code should not be null")
    @Column(name = "code", nullable = false, length = 5)
    private String code;

    @NotBlank(message = "Speciality's displayName should not be null")
    @Column(name = "display_name", nullable = false, length = 50)
    private String displayName;
}
