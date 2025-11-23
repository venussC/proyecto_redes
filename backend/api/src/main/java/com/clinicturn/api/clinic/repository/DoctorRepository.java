package com.clinicturn.api.clinic.repository;

import com.clinicturn.api.clinic.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
