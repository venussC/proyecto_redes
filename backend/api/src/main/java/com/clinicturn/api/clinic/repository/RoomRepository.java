package com.clinicturn.api.clinic.repository;

import com.clinicturn.api.clinic.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    boolean existsByClinicIdAndRoomNumber(Long clinicId, Integer roomNumber);

    List<Room> findByClinicIdOrderByRoomNumberAsc(Long clinicId);
}
