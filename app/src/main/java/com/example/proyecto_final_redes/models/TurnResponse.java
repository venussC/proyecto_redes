package com.example.proyecto_final_redes.models;

import com.google.gson.annotations.SerializedName;

public class TurnResponse {
    @SerializedName("id")
    private Long id;

    @SerializedName("number")
    private String number;

    @SerializedName("speciality")
    private TurnSpecialityResponse speciality;

    @SerializedName("patient")
    private TurnPatientResponse patient;

    @SerializedName("doctor")
    private TurnDoctorResponse doctor;

    @SerializedName("reason")
    private String reason;

    @SerializedName("status")
    private String status;

    @SerializedName("calledAt")
    private String calledAt;

    @SerializedName("seenAt")
    private String seenAt;

    @SerializedName("completedAt")
    private String completedAt;

    @SerializedName("cancelledAt")
    private String cancelledAt;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    public TurnResponse() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public TurnSpecialityResponse getSpeciality() { return speciality; }
    public void setSpeciality(TurnSpecialityResponse speciality) { this.speciality = speciality; }

    public TurnPatientResponse getPatient() { return patient; }
    public void setPatient(TurnPatientResponse patient) { this.patient = patient; }

    public TurnDoctorResponse getDoctor() { return doctor; }
    public void setDoctor(TurnDoctorResponse doctor) { this.doctor = doctor; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCalledAt() { return calledAt; }
    public void setCalledAt(String calledAt) { this.calledAt = calledAt; }

    public String getSeenAt() { return seenAt; }
    public void setSeenAt(String seenAt) { this.seenAt = seenAt; }

    public String getCompletedAt() { return completedAt; }
    public void setCompletedAt(String completedAt) { this.completedAt = completedAt; }

    public String getCancelledAt() { return cancelledAt; }
    public void setCancelledAt(String cancelledAt) { this.cancelledAt = cancelledAt; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}