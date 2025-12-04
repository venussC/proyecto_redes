package com.example.proyecto_final_redes.models;

import com.google.gson.annotations.SerializedName;

public class TurnStatusResponse {
    @SerializedName("id")
    private Long id;

    @SerializedName("name")
    private String name;

    public TurnStatusResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}