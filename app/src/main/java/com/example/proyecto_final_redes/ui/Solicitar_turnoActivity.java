package com.example.proyecto_final_redes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_final_redes.R;
import com.example.proyecto_final_redes.api.TurnApi;
import com.example.proyecto_final_redes.models.CreatePatientTurnRequest;
import com.example.proyecto_final_redes.models.TurnResponse;
import com.example.proyecto_final_redes.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Solicitar_turnoActivity extends AppCompatActivity {

    EditText txtNombre, txtCedula, txtTelefono, txtMotivo;
    Spinner spEspecialidad;
    Button btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_turno);

        // Inputs
        txtNombre = findViewById(R.id.editNombre);
        txtCedula = findViewById(R.id.editCedula);
        txtTelefono = findViewById(R.id.editTelefono);
        txtMotivo = findViewById(R.id.editMotivo);
        spEspecialidad = findViewById(R.id.spinnerEspecialidad);
        btnConfirmar = findViewById(R.id.btnConfirmar);

        // Botón regresar
        ImageView btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        // API
        TurnApi api = RetrofitClient.getInstance(this).getTurnApi();

        btnConfirmar.setOnClickListener(v -> {

            String fullName = txtNombre.getText().toString();
            String dni = txtCedula.getText().toString();
            String phone = txtTelefono.getText().toString();
            String reason = txtMotivo.getText().toString();
            Long specialityId = (long) (spEspecialidad.getSelectedItemPosition() + 1);

            CreatePatientTurnRequest request = new CreatePatientTurnRequest(
                    fullName,
                    dni,
                    phone,
                    specialityId,
                    reason
            );

            api.createTurn(request).enqueue(new Callback<TurnResponse>() {
                @Override
                public void onResponse(Call<TurnResponse> call, Response<TurnResponse> response) {

                    if (response.isSuccessful()) {
                        TurnResponse turn = response.body();

                        Toast.makeText(
                                Solicitar_turnoActivity.this,
                                "Turno creado: " + turn.getNumber(),
                                Toast.LENGTH_LONG
                        ).show();

                    } else {
                        Toast.makeText(
                                Solicitar_turnoActivity.this,
                                "Error: " + response.code(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }

                @Override
                public void onFailure(Call<TurnResponse> call, Throwable t) {
                    Toast.makeText(
                            Solicitar_turnoActivity.this,
                            "Fallo: " + t.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                }
            });
        });
    }
}
