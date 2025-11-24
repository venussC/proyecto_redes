package com.clinicturn.api.clinic.service;

import com.clinicturn.api.clinic.dto.request.CreateRoomRequest;
import com.clinicturn.api.clinic.dto.response.RoomResponse;

import java.util.List;

public interface RoomService {

    RoomResponse create(CreateRoomRequest request);

    RoomResponse getById(Long roomId);

    List<RoomResponse> getAllAvailablesByClinicId(Long clinicId);
}
