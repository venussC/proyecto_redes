package com.example.proyecto_final_redes.repository;

import android.content.Context;
import android.util.Log;
import com.example.proyecto_final_redes.api.*;
import com.example.proyecto_final_redes.models.*;
import com.example.proyecto_final_redes.network.RetrofitClient;
import com.example.proyecto_final_redes.utils.AuthManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class ClinicTurnRepository {
    private static final String TAG = "ClinicTurnRepository";

    private RetrofitClient retrofitClient;
    private AuthManager authManager;

    private AuthApi authApi;
    private SpecialityApi specialityApi;
    private TurnApi turnApi;
    private PatientApi patientApi;
    private TurnStatusApi turnStatusApi;

    public ClinicTurnRepository(Context context) {
        retrofitClient = RetrofitClient.getInstance(context);
        authManager = new AuthManager(context);

        authApi = retrofitClient.getAuthApi();
        specialityApi = retrofitClient.getSpecialityApi();
        turnApi = retrofitClient.getTurnApi();
        patientApi = retrofitClient.getPatientApi();
        turnStatusApi = retrofitClient.getTurnStatusApi();
    }

    // ==================== AUTENTICACIÓN ====================

    public void login(String username, String password, final ApiCallback<LoginResponse> callback) {
        LoginRequest request = new LoginRequest(username, password);

        authApi.login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();

                    // Guardar tokens
                    authManager.saveTokens(
                            loginResponse.getAccessToken(),
                            loginResponse.getRefreshToken()
                    );

                    // Guardar username
                    authManager.saveUserInfo(null, username, null);

                    callback.onSuccess(loginResponse);
                } else {
                    callback.onError("Credenciales incorrectas");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e(TAG, "Login failed", t);
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    public void register(String username, String password, String phoneNumber,
                         final ApiCallback<RegisterResponse> callback) {
        RegisterRequest request = new RegisterRequest(username, password, phoneNumber);

        authApi.register(request).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    String errorMsg = "Error al registrarse";
                    if (response.code() == 409) {
                        errorMsg = "El usuario ya existe";
                    } else if (response.code() == 400) {
                        errorMsg = "Datos inválidos. Verifica el formato";
                    }
                    callback.onError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.e(TAG, "Register failed", t);
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    public void getCurrentUser(final ApiCallback<MeResponse> callback) {
        authApi.getCurrentUser().enqueue(new Callback<MeResponse>() {
            @Override
            public void onResponse(Call<MeResponse> call, Response<MeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MeResponse user = response.body();

                    // Guardar info del usuario
                    authManager.saveUserInfo(
                            user.getId(),
                            user.getUsername(),
                            user.getUsername()
                    );

                    callback.onSuccess(user);
                } else {
                    callback.onError("Error al obtener usuario: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MeResponse> call, Throwable t) {
                Log.e(TAG, "Get current user failed", t);
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    public void logout() {
        String refreshToken = authManager.getRefreshToken();
        if (refreshToken != null) {
            LogoutRequest request = new LogoutRequest(refreshToken);

            authApi.logout(request).enqueue(new Callback<LogoutResponse>() {
                @Override
                public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                    authManager.logout();
                    Log.d(TAG, "Logout successful");
                }

                @Override
                public void onFailure(Call<LogoutResponse> call, Throwable t) {
                    authManager.logout();
                    Log.e(TAG, "Logout failed, but cleared local session", t);
                }
            });
        } else {
            authManager.logout();
        }
    }

    public void refreshAccessToken(final ApiCallback<RefreshResponse> callback) {
        String refreshToken = authManager.getRefreshToken();
        if (refreshToken == null) {
            callback.onError("No refresh token available");
            return;
        }

        RefreshRequest request = new RefreshRequest(refreshToken);

        authApi.refreshToken(request).enqueue(new Callback<RefreshResponse>() {
            @Override
            public void onResponse(Call<RefreshResponse> call, Response<RefreshResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RefreshResponse refreshResponse = response.body();

                    // Actualizar tokens
                    authManager.saveTokens(
                            refreshResponse.getAccessToken(),
                            refreshResponse.getRefreshToken()
                    );

                    callback.onSuccess(refreshResponse);
                } else {
                    callback.onError("Error al refrescar token");
                }
            }

            @Override
            public void onFailure(Call<RefreshResponse> call, Throwable t) {
                Log.e(TAG, "Refresh token failed", t);
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    // ==================== ESPECIALIDADES ====================

    public void getAllSpecialities(final ApiCallback<List<SpecialityResponse>> callback) {
        specialityApi.getAllSpecialities().enqueue(new Callback<List<SpecialityResponse>>() {
            @Override
            public void onResponse(Call<List<SpecialityResponse>> call, Response<List<SpecialityResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener especialidades: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<SpecialityResponse>> call, Throwable t) {
                Log.e(TAG, "Get specialities failed", t);
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    // ==================== TURNOS ====================

    public void createTurn(String fullName, String dni, String phoneNumber,
                           Long specialityId, String reason, final ApiCallback<TurnResponse> callback) {
        CreatePatientTurnRequest request = new CreatePatientTurnRequest(
                fullName, dni, phoneNumber, specialityId, reason
        );

        turnApi.createTurn(request).enqueue(new Callback<TurnResponse>() {
            @Override
            public void onResponse(Call<TurnResponse> call, Response<TurnResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    String errorMsg = "Error al crear turno";
                    if (response.code() == 400) {
                        errorMsg = "Datos inválidos. Verifica la información";
                    } else if (response.code() == 401) {
                        errorMsg = "No estás autenticado. Inicia sesión";
                    }
                    callback.onError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<TurnResponse> call, Throwable t) {
                Log.e(TAG, "Create turn failed", t);
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    public void updateTurnStatus(Long turnId, String statusName, final ApiCallback<TurnResponse> callback) {
        UpdateTurnStatusRequest request = new UpdateTurnStatusRequest(turnId, statusName);

        turnApi.updateTurnStatus(turnId, request).enqueue(new Callback<TurnResponse>() {
            @Override
            public void onResponse(Call<TurnResponse> call, Response<TurnResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al actualizar estado: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<TurnResponse> call, Throwable t) {
                Log.e(TAG, "Update turn status failed", t);
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    public void getAvailableDoctorsByTurn(Long turnId, final ApiCallback<List<DoctorResponse>> callback) {
        turnApi.getAvailableDoctorsByTurn(turnId).enqueue(new Callback<List<DoctorResponse>>() {
            @Override
            public void onResponse(Call<List<DoctorResponse>> call, Response<List<DoctorResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener doctores: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<DoctorResponse>> call, Throwable t) {
                Log.e(TAG, "Get available doctors failed", t);
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    // ==================== PACIENTE ====================

    public void getMyPatientInfo(final ApiCallback<PatientResponse> callback) {
        patientApi.getMyPatientInfo().enqueue(new Callback<PatientResponse>() {
            @Override
            public void onResponse(Call<PatientResponse> call, Response<PatientResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener información del paciente: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<PatientResponse> call, Throwable t) {
                Log.e(TAG, "Get patient info failed", t);
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    // ==================== ESTADOS DE TURNO ====================

    public void getAllTurnStatuses(final ApiCallback<List<TurnStatusResponse>> callback) {
        turnStatusApi.getAllStatuses().enqueue(new Callback<List<TurnStatusResponse>>() {
            @Override
            public void onResponse(Call<List<TurnStatusResponse>> call, Response<List<TurnStatusResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener estados: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<TurnStatusResponse>> call, Throwable t) {
                Log.e(TAG, "Get turn statuses failed", t);
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    // ==================== CALLBACK INTERFACE ====================

    public interface ApiCallback<T> {
        void onSuccess(T result);
        void onError(String error);
    }
}