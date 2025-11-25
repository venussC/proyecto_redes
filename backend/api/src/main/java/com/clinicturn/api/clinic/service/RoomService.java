package com.clinicturn.api.clinic.service;

import com.clinicturn.api.clinic.dto.request.CreateRoomRequest;
import com.clinicturn.api.clinic.dto.request.UpdateRoomRequest;
import com.clinicturn.api.clinic.dto.response.RoomResponse;
import com.clinicturn.api.clinic.model.Room;

import java.util.List;

public interface RoomService {

    RoomResponse create(CreateRoomRequest request);

    RoomResponse update(Long id, UpdateRoomRequest request);

    void updateIsAvailableStatus(Long id, Boolean isAvailable);

    RoomResponse getById(Long roomId);

    Room getByIdAndReturnEntity(Long roomId);

    List<RoomResponse> getAllAvailablesByClinicId(Long clinicId);

    List<RoomResponse> getAll();
}
