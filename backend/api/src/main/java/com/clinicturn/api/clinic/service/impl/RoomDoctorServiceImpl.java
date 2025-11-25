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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomDoctorServiceImpl implements RoomDoctorService {

    private final RoomDoctorRepository roomDoctorRepository;
    private final RoomService roomService;
    private final DoctorService doctorService;

    @Override
    @Transactional
    public RoomDoctorResponse create(CreateRoomDoctorRequest request) {
        Room room = roomService.getByIdAndReturnEntity(request.getRoomId());
        Doctor doctor = doctorService.getByIdAndReturnEntity(request.getDoctorId());
        validateIntegrity(room.getId(), doctor.getId());
        RoomDoctor roomDoctor = mapToEntity(room, doctor);
        RoomDoctor savedRoomDoctor = roomDoctorRepository.save(roomDoctor);
        return mapToResponse(savedRoomDoctor);
    }

    private void validateIntegrity(Long roomId, Long doctorId) {
        if (roomDoctorRepository.existsByRoomIdAndDoctorId(roomId, doctorId)) {
            throw new ResourceAlreadyExistsException("Room with id: " + roomId + " is already assigned to" +
                    "Doctor with id: " + doctorId);
        }
    }

    private RoomDoctor mapToEntity(Room room, Doctor doctor) {
        return RoomDoctor.builder()
                .room(room)
                .doctor(doctor)
                .build();
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
