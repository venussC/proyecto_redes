package com.clinicturn.api.clinic.service;

import com.clinicturn.api.clinic.dto.response.RoomResponse;

public interface RoomDoctorService {

    RoomResponse findLatestRoomFromDoctor(Long doctorId);
}
