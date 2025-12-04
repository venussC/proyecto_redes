package com.example.proyecto_final_redes.models;

import com.google.gson.annotations.SerializedName;

public class TurnSpecialityResponse {
    @SerializedName("id")
    private Long id;

    @SerializedName("code")
    private String code;

    public TurnSpecialityResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
}
