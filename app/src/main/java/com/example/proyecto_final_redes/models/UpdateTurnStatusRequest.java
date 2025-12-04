package com.example.proyecto_final_redes.models;

import com.google.gson.annotations.SerializedName;

public class UpdateTurnStatusRequest {
    @SerializedName("id")
    private Long id;

    @SerializedName("statusName")
    private String statusName;

    public UpdateTurnStatusRequest(Long id, String statusName) {
        this.id = id;
        this.statusName = statusName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStatusName() { return statusName; }
    public void setStatusName(String statusName) { this.statusName = statusName; }
}