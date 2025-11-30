package com.clinicturn.api.clinic.repository;

import com.clinicturn.api.clinic.model.RoomDoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoomDoctorRepository extends JpaRepository<RoomDoctor, Long> {

    boolean existsByRoomIdAndDoctorId(Long roomId, Long doctorId);

    Optional<RoomDoctor> findTopByDoctor_IdOrderByAssignedAtDesc(Long doctorId);

    @Query("""
    SELECT rd.room.roomNumber
    FROM RoomDoctor rd
    WHERE rd.doctor.id = :doctorId
    ORDER BY rd.assignedAt DESC
    """)
    Optional<String> findLastRoomNumberByDoctorId(@Param("doctorId") Long doctorId);
}
