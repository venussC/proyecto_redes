package com.example.proyecto_final_redes.models;

import com.google.gson.annotations.SerializedName;

public class DoctorResponse {
    @SerializedName("id")
    private Long id;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("speciality")
    private SpecialityResponse speciality;

    @SerializedName("email")
    private String email;

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("isActive")
    private Boolean isActive;

    @SerializedName("room")
    private RoomResponse room;

    public DoctorResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public SpecialityResponse getSpeciality() { return speciality; }
    public void setSpeciality(SpecialityResponse speciality) { this.speciality = speciality; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public RoomResponse getRoom() { return room; }
    public void setRoom(RoomResponse room) { this.room = room; }
}