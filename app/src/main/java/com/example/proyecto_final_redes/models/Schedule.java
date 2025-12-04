package com.example.proyecto_final_redes.models;

public class Schedule {
    private int id;
    private int clinicId;
    private String weekDay;
    private String opening;
    private String closing;
    private boolean isClosed;

    public int getId() { return id; }
    public int getClinicId() { return clinicId; }
    public String getWeekDay() { return weekDay; }
    public String getOpening() { return opening; }
    public String getClosing() { return closing; }
    public boolean isClosed() { return isClosed; }
}
