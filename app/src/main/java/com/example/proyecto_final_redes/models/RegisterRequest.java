package com.example.proyecto_final_redes.models;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("username")
    private String username; // 5-50 caracteres, alfanumérico con . y _

    @SerializedName("password")
    private String password; // Min 10 caracteres, debe tener mayúscula, minúscula, número y símbolo

    @SerializedName("phoneNumber")
    private String phoneNumber; // Formato: 6XXX-XXXX (9 caracteres)

    public RegisterRequest(String username, String password, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
