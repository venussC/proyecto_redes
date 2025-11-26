package com.clinicturn.api.patient.model;

import com.clinicturn.api.auth.model.ClinicUser;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "patient", name = "patient")
public class Patient {

    private Long id;

    private ClinicUser user;
}
