package com.clinicturn.api.clinic.repository;

import com.clinicturn.api.clinic.model.RoomDoctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomDoctorRepository extends JpaRepository<RoomDoctor, Long> {

    Optional<RoomDoctor> findTopByDoctor_IdOrderByAssignedAtDesc(Long doctorId);
}
