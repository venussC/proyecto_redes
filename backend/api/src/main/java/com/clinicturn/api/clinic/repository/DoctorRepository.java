package com.clinicturn.api.clinic.repository;

import com.clinicturn.api.clinic.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findByIsActiveTrue();

    List<Doctor> findByIsActiveTrueAndSpeciality_Code(String code);
}
