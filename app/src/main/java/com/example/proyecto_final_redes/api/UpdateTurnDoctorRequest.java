package com.example.proyecto_final_redes.api;

import com.example.proyecto_final_redes.models.*;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

class UpdateTurnDoctorRequest {
    @com.google.gson.annotations.SerializedName("id")
    private Long id;

    @com.google.gson.annotations.SerializedName("doctorId")
    private Long doctorId;

    public UpdateTurnDoctorRequest(Long id, Long doctorId) {
        this.id = id;
        this.doctorId = doctorId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
}