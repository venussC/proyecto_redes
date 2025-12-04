package com.example.proyecto_final_redes.models;

import com.google.gson.annotations.SerializedName;

public class RoomResponse {
    @SerializedName("id")
    private Long id;

    @SerializedName("clinicId")
    private Long clinicId;

    @SerializedName("roomNumber")
    private Integer roomNumber;

    @SerializedName("isActive")
    private Boolean isActive;

    @SerializedName("isAvailable")
    private Boolean isAvailable;

    public RoomResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClinicId() { return clinicId; }
    public void setClinicId(Long clinicId) { this.clinicId = clinicId; }

    public Integer getRoomNumber() { return roomNumber; }
    public void setRoomNumber(Integer roomNumber) { this.roomNumber = roomNumber; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }
}