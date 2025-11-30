package com.clinicturn.api.clinic.service.impl;

import com.clinicturn.api.clinic.dto.request.CreateRoomDoctorRequest;
import com.clinicturn.api.clinic.dto.response.RoomDoctorResponse;
import com.clinicturn.api.clinic.exception.InactiveRoomException;
import com.clinicturn.api.clinic.exception.UnavailableRoomException;
import com.clinicturn.api.clinic.model.Doctor;
import com.clinicturn.api.clinic.model.Room;
import com.clinicturn.api.clinic.model.RoomDoctor;
import com.clinicturn.api.clinic.repository.RoomDoctorRepository;
import com.clinicturn.api.clinic.service.DoctorService;
import com.clinicturn.api.clinic.service.RoomDoctorService;
import com.clinicturn.api.clinic.service.RoomService;
import com.clinicturn.api.common.exception.ResourceAlreadyExistsException;
import com.clinicturn.api.common.exception.ResourceNotFoundException;
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
        Room newRoom = roomService.getByIdAndReturnEntity(request.getRoomId());
        Doctor doctor = doctorService.getByIdAndReturnEntity(request.getDoctorId());

        validateRoomIsAvailable(newRoom);

        Optional<RoomDoctor> existingOpt = roomDoctorRepository
                .findTopByDoctor_IdOrderByAssignedAtDesc(doctor.getId());

        RoomDoctor roomDoctor;

        if (existingOpt.isPresent()) {
            RoomDoctor existing = existingOpt.get();
            Room oldRoom = existing.getRoom();

            validateIntegrity(oldRoom.getId(), newRoom.getId(), doctor.getId());

            roomService.updateIsAvailableStatus(oldRoom.getId(), true);
            roomService.updateIsAvailableStatus(newRoom.getId(), false);

            existing.setRoom(newRoom);
            roomDoctor = existing;
        } else {
            roomService.updateIsAvailableStatus(newRoom.getId(), false);
            roomDoctor = RoomDoctor.builder()
                    .room(newRoom)
                    .doctor(doctor)
                    .build();
        }
        RoomDoctor saved = roomDoctorRepository.save(roomDoctor);
        return mapToResponse(saved);
    }

    @Override
    public String getRoomNumberFromDoctorByDoctorId(Long doctorId) {
        return doctorId == null ? null :
                roomDoctorRepository.findLastRoomNumberByDoctorId(doctorId).orElse(null);
    }

    private void validateRoomIsAvailable(Room room) {
        if (!room.getIsActive()) {
            throw new InactiveRoomException(
                    "Room with id: " + room.getId() + " is inactive and cannot be assigned."
            );
        }

        if (!room.getIsAvailable()) {
            throw new UnavailableRoomException(
                    "Room with id: " + room.getId() + " is not available for assignment."
            );
        }
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
