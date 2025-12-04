package com.example.proyecto_final_redes.models;

import com.google.gson.annotations.SerializedName;

public class MeResponse {
    @SerializedName("id")
    private Long id;

    @SerializedName("username")
    private String username;

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("roles")
    private String[] roles;

    public MeResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String[] getRoles() { return roles; }
    public void setRoles(String[] roles) { this.roles = roles; }
}