package com.example.proyecto_final_redes.models;

import com.google.gson.annotations.SerializedName;

public class CreatePatientTurnRequest {
    @SerializedName("fullName")
    private String fullName; // Max 300 caracteres

    @SerializedName("dni")
    private String dni; // Max 20 caracteres

    @SerializedName("phoneNumber")
    private String phoneNumber; // Formato XXX-XXXX o XXXX-XXXX

    @SerializedName("specialityId")
    private Long specialityId;

    @SerializedName("reason")
    private String reason; // Max 500 caracteres

    public CreatePatientTurnRequest(String fullName, String dni, String phoneNumber,
                                    Long specialityId, String reason) {
        this.fullName = fullName;
        this.dni = dni;
        this.phoneNumber = phoneNumber;
        this.specialityId = specialityId;
        this.reason = reason;
    }

    // Getters y Setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public Long getSpecialityId() { return specialityId; }
    public void setSpecialityId(Long specialityId) { this.specialityId = specialityId; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}