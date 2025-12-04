package com.example.proyecto_final_redes.models;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    @SerializedName("message")
    private String message;

    public RegisterResponse() {}

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}