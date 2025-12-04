package com.example.proyecto_final_redes.api;
import com.example.proyecto_final_redes.models.*;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

public interface PatientApi {
    @GET("api/v1/patient/patient/me")
    Call<PatientResponse> getMyPatientInfo();
}