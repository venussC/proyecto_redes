package com.example.proyecto_final_redes.models;

import com.google.gson.annotations.SerializedName;

public class TurnPatientResponse {
    @SerializedName("id")
    private Long id;

    @SerializedName("fullName")
    private String fullName;

    public TurnPatientResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
}