package com.example.proyecto_final_redes.models;

import com.google.gson.annotations.SerializedName;

public class LogoutResponse {
    @SerializedName("message")
    private String message;

    public LogoutResponse() {}

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}