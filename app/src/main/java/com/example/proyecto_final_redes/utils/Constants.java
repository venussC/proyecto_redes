package com.example.proyecto_final_redes.utils;

public class Constants {
    // URL base de tu API
    // Usa 10.0.2.2 para emulador de Android (apunta a localhost de tu PC)
    // Usa tu IP local (ej: 192.168.1.X) para dispositivo físico
    public static final String BASE_URL = "http://10.0.2.2:8080/";

    // SharedPreferences keys
    public static final String PREFS_NAME = "ClinicTurnPrefs";
    public static final String KEY_ACCESS_TOKEN = "access_token";
    public static final String KEY_REFRESH_TOKEN = "refresh_token";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USER_EMAIL = "user_email";
    public static final String KEY_USER_NAME = "user_name";

    // Request codes
    public static final int REQUEST_LOGIN = 1001;
    public static final int REQUEST_SELECT_SPECIALTY = 1002;

    // Turn status (nombres exactos de la API)
    public static final String STATUS_PENDING = "PENDIENTE";
    public static final String STATUS_CONFIRMED = "CONFIRMADO";
    public static final String STATUS_IN_ATTENTION = "EN_ATENCION";
    public static final String STATUS_COMPLETED = "COMPLETADO";
    public static final String STATUS_CANCELLED = "CANCELADO";

    // Validaciones de campos
    public static final int USERNAME_MIN_LENGTH = 5;
    public static final int USERNAME_MAX_LENGTH = 50;
    public static final int PASSWORD_MIN_LENGTH = 10;
    public static final int PASSWORD_MAX_LENGTH = 128;
    public static final int PHONE_LENGTH = 9; // Formato: 6XXX-XXXX
    public static final int DNI_MAX_LENGTH = 20;
    public static final int FULLNAME_MAX_LENGTH = 300;
    public static final int REASON_MAX_LENGTH = 500;

    // Patrones de validación
    public static final String USERNAME_PATTERN = "^(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]{5,50}(?<![_.])$";
    public static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{10,128}$";
    public static final String PHONE_PATTERN = "^6\\d{3}-\\d{4}$"; // Ej: 6123-4567
}