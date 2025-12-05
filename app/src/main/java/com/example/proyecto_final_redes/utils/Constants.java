package com.example.proyecto_final_redes.utils;

public class Constants {

    // API
    public static final String BASE_URL = "http://10.0.2.2:8080/";

    // SharedPreferences (UNO SOLO)
    public static final String PREFS_NAME = "AppPrefs";

    public static final String KEY_ONBOARDING = "onboarding_complete";
    public static final String KEY_ACCESS_TOKEN = "access_token";
    public static final String KEY_REFRESH_TOKEN = "refresh_token";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USER_EMAIL = "user_email";
    public static final String KEY_USER_NAME = "user_name";

    // Request codes
    public static final int REQUEST_LOGIN = 1001;
    public static final int REQUEST_SELECT_SPECIALTY = 1002;

    // Turnos
    public static final String STATUS_PENDING = "PENDIENTE";
    public static final String STATUS_CONFIRMED = "CONFIRMADO";
    public static final String STATUS_IN_ATTENTION = "EN_ATENCION";
    public static final String STATUS_COMPLETED = "COMPLETADO";
    public static final String STATUS_CANCELLED = "CANCELADO";

    // Validaciones
    public static final int USERNAME_MIN_LENGTH = 5;
    public static final int USERNAME_MAX_LENGTH = 50;
    public static final int PASSWORD_MIN_LENGTH = 10;
    public static final int PASSWORD_MAX_LENGTH = 128;
    public static final int PHONE_LENGTH = 9;
    public static final int DNI_MAX_LENGTH = 20;
    public static final int FULLNAME_MAX_LENGTH = 300;
    public static final int REASON_MAX_LENGTH = 500;

    // Patrones
    public static final String USERNAME_PATTERN = "^(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]{5,50}(?<![_.])$";
    public static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{10,128}$";
    public static final String PHONE_PATTERN = "^6\\d{3}-\\d{4}$";
}
