package com.example.proyecto_final_redes.api;
import com.example.proyecto_final_redes.models.*;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;
public interface TurnApi {
    // Paciente solicita turno
    @POST("api/v1/patient/turn")
    Call<TurnResponse> createTurn(@Body CreatePatientTurnRequest request);

    // Cambiar estado del turno
    @PATCH("api/v1/patient/turn/{id}/status")
    Call<TurnResponse> updateTurnStatus(
            @Path("id") Long turnId,
            @Body UpdateTurnStatusRequest request
    );

    // Obtener doctores disponibles para un turno
    @GET("api/v1/patient/turn/{id}/doctor")
    Call<List<DoctorResponse>> getAvailableDoctorsByTurn(@Path("id") Long turnId);

    // Asignar doctor a turno (para admin/recepcionista)
    @PATCH("api/v1/patient/turn/{id}/doctor")
    Call<TurnResponse> assignDoctor(
            @Path("id") Long turnId,
            @Body UpdateTurnDoctorRequest request
    );
}