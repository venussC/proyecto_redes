package com.clinicturn.api.clinic.service.impl;

import com.clinicturn.api.clinic.dto.request.CreateRoomRequest;
import com.clinicturn.api.clinic.dto.response.RoomResponse;
import com.clinicturn.api.clinic.model.Clinic;
import com.clinicturn.api.clinic.model.Room;
import com.clinicturn.api.clinic.repository.RoomRepository;
import com.clinicturn.api.clinic.service.ClinicService;
import com.clinicturn.api.clinic.service.RoomService;
import com.clinicturn.api.common.exception.ResourceAlreadyExistsException;
import com.clinicturn.api.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final ClinicService clinicService;

    @Override
    @Transactional
    public RoomResponse create(CreateRoomRequest request) {
        Clinic clinic = clinicService.findByIdAndReturnEntity(request.getClinicId());
        validateNotExistsByClinicIdAndRoomNumber(request.getClinicId(),request.getRoomNumber());
        Room room = mapFromCreateDTOtoEntity(clinic, request);
        Room savedRoom = roomRepository.save(room);
        return mapToResponse(savedRoom);
    }

    @Override
    public RoomResponse getById(Long roomId) {
        Room entity = validateExistsById(roomId);
        return mapToResponse(entity);
    }

    @Override
    public Room getByIdAndReturnEntity(Long roomId) {
        return validateExistsById(roomId);
    }

    @Override
    public List<RoomResponse> getAllAvailablesByClinicId(Long clinicId) {
        clinicService.assertExistsById(clinicId);
        return roomRepository.findByClinicIdOrderByRoomNumberAsc(clinicId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    private void validateNotExistsByClinicIdAndRoomNumber(Long clinicId, Integer roomNumber) {
        if (roomRepository.existsByClinicIdAndRoomNumber(clinicId, roomNumber)) {
            throw new ResourceAlreadyExistsException("Room already exists for clinic id: " + clinicId +
                    " on room " + roomNumber
            );
        }
    }

    private Room validateExistsById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + roomId));
    }

    private Room mapFromCreateDTOtoEntity(Clinic clinic, CreateRoomRequest request) {
        return Room.builder()
                .clinic(clinic)
                .roomNumber(request.getRoomNumber())
                .build();
    }

    private RoomResponse mapToResponse(Room entity) {
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
