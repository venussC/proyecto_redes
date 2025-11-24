package com.clinicturn.api.clinic.service.impl;

import com.clinicturn.api.clinic.dto.response.RoomResponse;
import com.clinicturn.api.clinic.model.Room;
import com.clinicturn.api.clinic.repository.RoomDoctorRepository;
import com.clinicturn.api.clinic.service.RoomDoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomDoctorServiceImpl implements RoomDoctorService {

    private final RoomDoctorRepository roomDoctorRepository;

    @Override
    public RoomResponse findLatestRoomFromDoctor(Long doctorId) {
        return roomDoctorRepository.findTopByDoctor_IdOrderByAssignedAtDesc(doctorId)
                .map(roomDoctor -> mapToRoomResponse(roomDoctor.getRoom()))
                .orElse(null);
    }

    private RoomResponse mapToRoomResponse(Room entity) {
        return RoomResponse.builder()
                .id(entity.getId())
                .clinicId(entity.getClinic().getId())
                .roomNumber(entity.getRoomNumber())
                .isActive(entity.getIsActive())
                .isAvailable(entity.getIsAvailable())
                .createdAt(entity.getCreatedAt().atZone(ZoneOffset.UTC).toInstant())
                .updatedAt(entity.getUpdatedAt().atZone(ZoneOffset.UTC).toInstant())
                .build();
    }
}
