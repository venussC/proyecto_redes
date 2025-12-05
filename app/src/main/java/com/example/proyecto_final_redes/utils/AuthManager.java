package com.example.proyecto_final_redes.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthManager {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public AuthManager(Context context) {
        prefs = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    // Guardar tokens después del login
    public void saveTokens(String accessToken, String refreshToken) {
        editor.putString(Constants.KEY_ACCESS_TOKEN, accessToken);
        editor.putString(Constants.KEY_REFRESH_TOKEN, refreshToken);
        editor.apply();
    }

    // Obtener access token
    public String getAccessToken() {
        return prefs.getString(Constants.KEY_ACCESS_TOKEN, null);
    }

    // Obtener refresh token
    public String getRefreshToken() {
        return prefs.getString(Constants.KEY_REFRESH_TOKEN, null);
    }

    // Guardar información del usuario
    public void saveUserInfo(Long userId, String email, String name) {
        editor.putLong(Constants.KEY_USER_ID, userId);
        editor.putString(Constants.KEY_USER_EMAIL, email);
        editor.putString(Constants.KEY_USER_NAME, name);
        editor.apply();
    }

    // Obtener ID del usuario
    public Long getUserId() {
        return prefs.getLong(Constants.KEY_USER_ID, -1);
    }

    // Obtener email del usuario
    public String getUserEmail() {
        return prefs.getString(Constants.KEY_USER_EMAIL, null);
    }

    // Obtener nombre del usuario
    public String getUserName() {
        return prefs.getString(Constants.KEY_USER_NAME, null);
    }

    // Verificar si el usuario está autenticado
    public boolean isLoggedIn() {
        String token = getAccessToken();
        return token != null && token.trim().length() > 10;
    }

    // Cerrar sesión (limpiar todos los datos)
    public void logout() {
        editor.clear();
        editor.apply();
    }

    // Actualizar solo el access token (después de refresh)
    public void updateAccessToken(String newAccessToken) {
        editor.putString(Constants.KEY_ACCESS_TOKEN, newAccessToken);
        editor.apply();
    }
}