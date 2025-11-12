package com.clinicturn.api.auth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Entity
@Table(schema = "auth", name = "clinic_role_user",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"role_id", "user_id"}, name = "role_user_roleId_userId_unique")
        },
        indexes = {
            @Index(columnList = "role_id", name = "fk_clinic_role_user_clinic_role_idx"),
            @Index(columnList = "user_id", name = "fk_clinic_role_user_clinic_user_idx")
        }
)
public class ClinicRoleUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "ClinicRoleUser's role should not be null")
    @JoinColumn(name = "role_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_clinic_role_user_clinic_role"))
    private ClinicRole role;

    @ManyToOne
    @NotNull(message = "ClinicRoleUser's user should not be null")
    @JoinColumn(name = "user_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_clinic_role_user_clinic_user"))
    private ClinicUser user;

    @EqualsAndHashCode.Include
    public Long getRoleId() {
        return role != null ? role.getId() : null;
    }

    @EqualsAndHashCode.Include
    public Long getUserId() {
        return user != null ? user.getId() : null;
    }
}
