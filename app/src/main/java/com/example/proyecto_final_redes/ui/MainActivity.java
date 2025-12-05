package com.example.proyecto_final_redes.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.proyecto_final_redes.models.Schedule;
import com.example.proyecto_final_redes.network.RetrofitClient;
import com.example.proyecto_final_redes.utils.AuthManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    // --- Información clínica ---
    TextView txtDireccionClinica, txtTelefonoClinica, txtHorarioClinica;

    // --- Mostrar turno actual ---
    TextView txtNumeroTurno;

    Button btnLlamar, btnVerUbicacion;

    // Coordenadas
    double clinicaLat = 0.0;
    double clinicaLng = 0.0;

    // Botones
    Button btnSolicitarTurno;
    MaterialButton btnConsultarTurno;

    // API
    ClinicApi clinicApi;

    // Auth (heredado de BaseActivity)
    private ImageView btnUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializar AuthManager (ya se hace en setupToolbar de BaseActivity)
        // authManager ya está disponible

        // Inflar layout
        setContentView(R.layout.activity_main);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ============================
        //         TOOLBAR
        // ============================
        setupToolbar(); // Método heredado de BaseActivity

        // ============================
        //      Inicializar API
        // ============================
        clinicApi = RetrofitClient.getInstance(this).getClinicApi();

        // ============================
        //    Referencias de vistas
        // ============================
        txtDireccionClinica = findViewById(R.id.txtDireccionClinica);
        txtTelefonoClinica = findViewById(R.id.txtTelefonoClinica);
        txtHorarioClinica = findViewById(R.id.txtHorarioClinica);
        btnLlamar = findViewById(R.id.btnLlamar);
        btnVerUbicacion = findViewById(R.id.btnVerUbicacion);
        btnSolicitarTurno = findViewById(R.id.btnSolicitarTurno);
        btnConsultarTurno = findViewById(R.id.btnConsultarTurno);

        txtNumeroTurno = findViewById(R.id.txtNumeroTurno);

        FloatingActionButton btnLogout = findViewById(R.id.btnLogout);

// Mostrar solo si hay login
        if (authManager.isLoggedIn()) {
            btnLogout.setVisibility(View.VISIBLE);
        } else {
            btnLogout.setVisibility(View.GONE);
        }

        btnLogout.setOnClickListener(v -> cerrarSesion());


        // ============================
        //     Cargar info clínica
        // ============================
        cargarInfoClinica();

        // ============================
        //      Click listeners
        // ============================
        configurarBotones();

        configurarToolbar();
    }

    // ============================
    //    Configurar Toolbar
    // ============================
    private void configurarToolbar() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.mi_toolbar);
        setSupportActionBar(toolbar);

        // Título "Inicio" → Regresar a MainActivity (refrescar)
        TextView txtInicio = toolbar.findViewById(R.id.txtInicio);
        txtInicio.setOnClickListener(v -> {
            // Si ya estamos en MainActivity, solo refrescar
            finish();
        });

        // Botón Usuario → Login o Perfil según estado
        btnUsuario = toolbar.findViewById(R.id.btnUsuario);
        btnUsuario.setOnClickListener(v -> {
            if (authManager.isLoggedIn()) {
                // Usuario logueado → Ir a perfil/turnos
                startActivity(new Intent(MainActivity.this, TurnoConfirmado.class));
            } else {
                // Usuario NO logueado → Ir a Login
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

    // ============================
    //    Configurar Botones
    // ============================
    private void configurarBotones() {

        // Botón Consultar Turno → Validar login
        btnSolicitarTurno.setOnClickListener(v -> {
            if (authManager.isLoggedIn()) {
                startActivity(new Intent(MainActivity.this, Solicitar_turnoActivity.class));
            } else {
                Toast.makeText(this, "Debes iniciar sesión para solicitar un turno", Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        btnConsultarTurno.setOnClickListener(v -> {
            if (authManager.isLoggedIn()) {
                startActivity(new Intent(MainActivity.this, TurnoConfirmado.class));
            } else {
                Toast.makeText(this, "Debes iniciar sesión para consultar tus turnos", Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        btnVerUbicacion.setOnClickListener(v -> abrirMapa());


        // Botón Llamar → No requiere login
        btnLlamar.setOnClickListener(v -> llamarClinica());
    }

    // ============================
    //   API: Información clínica
    // ============================
    private void cargarInfoClinica() {
        // Llamada siempre, aunque no haya sesión
        clinicApi.getClinicById(1).enqueue(new Callback<ClinicResponse>() {
            @Override
            public void onResponse(Call<ClinicResponse> call, Response<ClinicResponse> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.e("CLINIC_API", "Error: " + response.code() + " - " + response.message());
                    Toast.makeText(MainActivity.this, "Error cargando información de la clínica", Toast.LENGTH_SHORT).show();
                    return;
                }

                ClinicResponse info = response.body();
                txtDireccionClinica.setText(info.getAddress());
                txtTelefonoClinica.setText(info.getPhoneNumber());

                Log.d("API_DEBUG", "Respuesta COMPLETA: " + new Gson().toJson(response.body()));
                Log.d("API_DEBUG", "Horarios: " + new Gson().toJson(response.body().getSchedules()));


                StringBuilder builder = new StringBuilder();

                for (Schedule s : info.getSchedules()) {
                    if (s.isClosed()) {
                        builder.append(s.getWeekDay()).append(": Cerrado\n");
                    } else {
                        builder.append(s.getWeekDay())
                                .append(": ")
                                .append(s.getOpening())
                                .append(" - ")
                                .append(s.getClosing())
                                .append("\n");
                    }
                }

                txtHorarioClinica.setText(builder.toString());



                clinicaLat = info.getLatitude();
                clinicaLng = info.getLongitude();
            }

            @Override
            public void onFailure(Call<ClinicResponse> call, Throwable t) {
                Log.e("CLINIC_API", "Error al cargar clínica: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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

    // ============================
    //     onResume (refrescar UI)
    // ============================
    @Override
    protected void onResume() {
        super.onResume();
        // Refrescar la UI cuando volvemos a esta actividad
        // Por si el usuario se acaba de loguear
        actualizarUISegunEstado();

        // =====================================================
        // 🔥 CARGAR EL ULTIMO TURNO GUARDADO
        // =====================================================
        SharedPreferences prefs = getSharedPreferences("TURNO", MODE_PRIVATE);
        String ultimoTurno = prefs.getString("ULTIMO_TURNO", null);

        if (ultimoTurno != null) {
            txtNumeroTurno.setText("# " + ultimoTurno);
        } else {
            txtNumeroTurno.setText("—");
        }
    }

    private void actualizarUISegunEstado() {
        if (authManager.isLoggedIn()) {
            // Usuario logueado: Cambiar icono de usuario o mostrar nombre
            String username = authManager.getUserName();
            if (username != null) {
                // Aquí podrías cambiar el icono del btnUsuario o mostrar un saludo
                Log.d("MainActivity", "Usuario logueado: " + username);
            }
        } else {
            // Usuario NO logueado: Estado por defecto
            Log.d("MainActivity", "Usuario NO logueado");
        }
    }

    private void cerrarSesion() {
        // Borrar sesión
        authManager.logout();

        // Reset Retrofit para quitar los tokens
        RetrofitClient.resetInstance();

        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();

        // Redirigir a Login
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void abrirMapa() {
        if (clinicaLat == 0.0 || clinicaLng == 0.0) {
            Toast.makeText(this, "Ubicación no disponible", Toast.LENGTH_SHORT).show();
            return;
        }

        String uri = "geo:" + clinicaLat + "," + clinicaLng +
                "?q=" + clinicaLat + "," + clinicaLng + "(Clínica)";

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps"); // Forzar Google Maps

        try {
            startActivity(intent);
        } catch (Exception e) {
            // Si no tiene Google Maps, abre cualquier app de mapas
            Intent fallbackIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(fallbackIntent);
        }
    }




}