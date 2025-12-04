package com.example.proyecto_final_redes.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_final_redes.R;
import com.example.proyecto_final_redes.api.AuthApi;
import com.example.proyecto_final_redes.network.RetrofitClient;
import com.example.proyecto_final_redes.models.RegisterRequest;
import com.example.proyecto_final_redes.models.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private EditText edtUsuario, edtCorreo, edtContrasena;
    private Button btnRegistrarse;
    private TextView txtIrLogin;
    private ProgressBar progressBar;

    private AuthApi authApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // --- Inicializar vistas ---
        edtUsuario = findViewById(R.id.edtUsuario);
        edtCorreo = findViewById(R.id.edtCorreo);
        edtContrasena = findViewById(R.id.edtContrasena);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        txtIrLogin = findViewById(R.id.txtIrLogin);

        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);

        // Crear dinámicamente la ProgressBar sobre la pantalla
        addContentView(progressBar,
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                ));
        progressBar.setVisibility(View.GONE);

        // --- Retrofit ---
        authApi = RetrofitClient.getInstance(this).getAuthApi();

        // Botón Registrarse
        btnRegistrarse.setOnClickListener(v -> registrarUsuario());

        // Ir al login
        txtIrLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void registrarUsuario() {

        String usuario = edtUsuario.getText().toString().trim();
        String correo = edtCorreo.getText().toString().trim();
        String contraseña = edtContrasena.getText().toString().trim();

        // Validaciones
        if (usuario.isEmpty()) {
            edtUsuario.setError("Ingrese un nombre de usuario");
            return;
        }
        if (correo.isEmpty()) {
            edtCorreo.setError("Ingrese un correo");
            return;
        }
        if (!correo.contains("@")) {
            edtCorreo.setError("Correo inválido");
            return;
        }
        if (contraseña.isEmpty()) {
            edtContrasena.setError("Ingrese una contraseña");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        btnRegistrarse.setEnabled(false);

        // Crear objeto Request
        RegisterRequest request = new RegisterRequest(usuario, correo, contraseña);

        Call<RegisterResponse> call = authApi.register(request);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                progressBar.setVisibility(View.GONE);
                btnRegistrarse.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(RegistroActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                    // Volver al login
                    Intent i = new Intent(RegistroActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(RegistroActivity.this, "Error al registrarse", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                btnRegistrarse.setEnabled(true);
                Toast.makeText(RegistroActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}