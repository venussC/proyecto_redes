package com.example.proyecto_final_redes.api;

import com.example.proyecto_final_redes.models.*;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;
public interface SpecialityApi {
    @GET("api/v1/clinic/speciality")
    Call<List<SpecialityResponse>> getAllSpecialities();

    @GET("api/v1/clinic/speciality/{id}")
    Call<SpecialityResponse> getSpecialityById(@Path("id") Long id);
}
