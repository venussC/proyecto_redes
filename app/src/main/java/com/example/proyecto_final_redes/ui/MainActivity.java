package com.example.proyecto_final_redes.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_final_redes.R;
import com.example.proyecto_final_redes.api.ClinicApi;
import com.example.proyecto_final_redes.models.ClinicResponse;
import com.example.proyecto_final_redes.network.RetrofitClient;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // --- Información clínica ---
    TextView txtDireccionClinica, txtTelefonoClinica, txtHorarioClinica;
    Button btnLlamar;

    // Coordenadas
    double clinicaLat = 0.0;
    double clinicaLng = 0.0;

    // Botones
    Button btnSolicitarTurno;
    MaterialButton btnConsultarTurno;

    // API
    ClinicApi clinicApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // --- Inicializar API ---
        clinicApi = RetrofitClient.getInstance(this).getClinicApi();

        // --- Referencias ---
        txtDireccionClinica = findViewById(R.id.txtDireccionClinica);
        txtTelefonoClinica = findViewById(R.id.txtTelefonoClinica);
        txtHorarioClinica = findViewById(R.id.txtHorarioClinica);

        btnLlamar = findViewById(R.id.btnLlamar);
        btnSolicitarTurno = findViewById(R.id.btnSolicitarTurno);
        btnConsultarTurno = findViewById(R.id.btnConsultarTurno);

        // --- Cargar datos de la clínica ---
        cargarInfoClinica();

        btnSolicitarTurno.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, Solicitar_turnoActivity.class))
        );

        btnConsultarTurno.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, TurnoConfirmado.class))
        );

        btnLlamar.setOnClickListener(v -> llamarClinica());
    }

    // ============================
    //   API: Información clínica
    // ============================
    private void cargarInfoClinica() {

        clinicApi.getClinicById(1).enqueue(new Callback<ClinicResponse>() {
            @Override
            public void onResponse(Call<ClinicResponse> call, Response<ClinicResponse> response) {

                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(MainActivity.this, "Error cargando clínica", Toast.LENGTH_SHORT).show();
                    return;
                }

                ClinicResponse info = response.body();

                txtDireccionClinica.setText(info.getAddress());
                txtTelefonoClinica.setText(info.getPhoneNumber());
                txtHorarioClinica.setText("Ver horarios");

                clinicaLat = info.getLatitude();
                clinicaLng = info.getLongitude();
            }

            @Override
            public void onFailure(Call<ClinicResponse> call, Throwable t) {
                Log.e("CLINIC_API", "Error: " + t.getMessage());
            }
        });
    }

    // ============================
    //        Llamar clínica
    // ============================
    private void llamarClinica() {
        String phone = txtTelefonoClinica.getText().toString();

        if (phone.isEmpty()) {
            Toast.makeText(this, "Teléfono no disponible", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Se borra el onboarding cada vez que la app se cierra por completo
        getSharedPreferences("AppPrefs", MODE_PRIVATE)
                .edit()
                .remove("onboarding_complete")
                .apply();
    }

}
