package com.clinicturn.api.clinic.service;

import com.clinicturn.api.clinic.dto.request.CreateRoomDoctorRequest;
import com.clinicturn.api.clinic.dto.response.RoomDoctorResponse;

public interface RoomDoctorService {

    RoomDoctorResponse assignRoom(CreateRoomDoctorRequest request);

    String getRoomNumberFromDoctorByDoctorId(Long doctorId);
}
