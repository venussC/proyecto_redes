package com.example.proyecto_final_redes.api;

import com.example.proyecto_final_redes.models.*;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

public interface AuthApi {
    @POST("api/v1/auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("api/v1/auth/register")
    Call<RegisterResponse> register(@Body RegisterRequest request);

    @POST("api/v1/auth/refresh")
    Call<RefreshResponse> refreshToken(@Body RefreshRequest request);

    @POST("api/v1/auth/logout")
    Call<LogoutResponse> logout(@Body LogoutRequest request);

    @GET("api/v1/auth/me")
    Call<MeResponse> getCurrentUser();
}