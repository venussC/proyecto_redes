package com.example.proyecto_final_redes.api;
import com.example.proyecto_final_redes.models.*;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

public interface TurnStatusApi {
    @GET("api/v1/patient/turn/status")
    Call<List<TurnStatusResponse>> getAllStatuses();

    @GET("api/v1/patient/turn/status/{id}")
    Call<TurnStatusResponse> getStatusById(@Path("id") Long id);

    @GET("api/v1/patient/turn/status/by-name/{name}")
    Call<TurnStatusResponse> getStatusByName(@Path("name") String name);
}