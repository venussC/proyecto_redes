package com.example.proyecto_final_redes.models;

import com.google.gson.annotations.SerializedName;

public class Schedule {

    private int id;
    private int clinicId;

    @SerializedName("day")
    private String weekDay;

    private String opening;
    private String closing;

    @SerializedName("isClosed")
    private boolean isClosed;

    public int getId() { return id; }
    public int getClinicId() { return clinicId; }
    public String getWeekDay() { return weekDay; }
    public String getOpening() { return opening; }
    public String getClosing() { return closing; }
    public boolean isClosed() { return isClosed; }
}
