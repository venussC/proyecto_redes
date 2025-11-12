package com.clinicturn.api.auth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(schema = "auth", name = "clinic_role",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "code", name = "role_code_unique"),
            @UniqueConstraint(columnNames = "display_name", name = "role_displayName_unique")
        }
)
public class ClinicRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "ClinicRole's code should not be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false, length = 3)
    private ClinicRoleType code;

    @NotBlank(message = "ClinicRole's displayName should not be blank")
    @Column(name = "display_name", nullable = false, length = 50)
    private String displayName;
}
