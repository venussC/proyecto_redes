package com.example.proyecto_final_redes.models;

public class ClinicRequest {
    private String name;
    private String address;
    private String phoneNumber;
    private double latitude;
    private double longitude;

    public ClinicRequest(String name, String address, String phoneNumber, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
