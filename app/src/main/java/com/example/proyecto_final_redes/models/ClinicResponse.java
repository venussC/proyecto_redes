package com.example.proyecto_final_redes.models;

import java.util.List;

public class ClinicResponse {
    private int id;
    private String name;
    private String address;
    private String phoneNumber;
    private double latitude;
    private double longitude;
    private List<Schedule> schedules;
    private String createdAt;
    private String updatedAt;

    // Getters...

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}