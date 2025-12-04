package com.example.proyecto_final_redes.api;


import com.example.proyecto_final_redes.models.Clinic;
import com.example.proyecto_final_redes.models.ClinicRequest;
import com.example.proyecto_final_redes.models.ClinicResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


    public interface ClinicApi {

        // Crear clínica
        @POST("/api/v1/clinic/clinic")
        Call<ClinicRequest> createClinic(@Body ClinicRequest clinic);

        // Obtener clínica por ID
        @GET("/api/v1/clinic/clinic/{id}")
        Call<ClinicResponse> getClinicById(@Path("id") int id);
    }


