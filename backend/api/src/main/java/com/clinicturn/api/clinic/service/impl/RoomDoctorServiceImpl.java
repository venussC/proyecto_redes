package com.clinicturn.api.clinic.service.impl;

import com.clinicturn.api.clinic.dto.request.CreateRoomDoctorRequest;
import com.clinicturn.api.clinic.dto.response.RoomDoctorResponse;
import com.clinicturn.api.clinic.model.Doctor;
import com.clinicturn.api.clinic.model.Room;
import com.clinicturn.api.clinic.model.RoomDoctor;
import com.clinicturn.api.clinic.repository.RoomDoctorRepository;
import com.clinicturn.api.clinic.service.DoctorService;
import com.clinicturn.api.clinic.service.RoomDoctorService;
import com.clinicturn.api.clinic.service.RoomService;
import com.clinicturn.api.common.exception.ResourceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomDoctorServiceImpl implements RoomDoctorService {

    private final RoomDoctorRepository roomDoctorRepository;
    private final RoomService roomService;
    private final DoctorService doctorService;

    @Override
    @Transactional
    public RoomDoctorResponse assignRoom(CreateRoomDoctorRequest request) {
        Room room = roomService.getByIdAndReturnEntity(request.getRoomId());
        Doctor doctor = doctorService.getByIdAndReturnEntity(request.getDoctorId());

        Optional<RoomDoctor> existing = roomDoctorRepository
                .findTopByDoctor_IdOrderByAssignedAtDesc(doctor.getId());

        RoomDoctor roomDoctor;

        if (existing.isPresent()) {
            roomDoctor = existing.get();
            validateIntegrity(roomDoctor.getRoom().getId(), room.getId(), doctor.getId());
            roomDoctor.setRoom(room);
        } else {
            roomDoctor = RoomDoctor.builder()
                    .room(room)
                    .doctor(doctor)
                    .build();
        }
        RoomDoctor saved = roomDoctorRepository.save(roomDoctor);
        return mapToResponse(saved);
    }

    private void validateIntegrity(Long entityRoomId, Long requestRoomId, Long doctorId) {
        if (entityRoomId.equals(requestRoomId)) {
            throw new ResourceAlreadyExistsException(
                    "Doctor with id " + doctorId +
                            " is already assigned to room " + entityRoomId
            );
        }
    }

    private RoomDoctorResponse mapToResponse(RoomDoctor entity) {
        return RoomDoctorResponse.builder()
                .id(entity.getId())
                .doctorId(entity.getDoctor().getId())
                .doctorName(entity.getDoctor().getFullName())
                .roomId(entity.getRoom().getId())
                .roomNumber(entity.getRoom().getRoomNumber())
                .assignedAt(entity.getAssignedAt().atZone(ZoneOffset.UTC).toInstant())
                .build();
    }
}
