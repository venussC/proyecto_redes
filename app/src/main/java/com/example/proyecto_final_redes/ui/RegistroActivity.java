package com.example.proyecto_final_redes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

    private EditText edtUsuario, edtTelefono, edtContrasena;
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
        edtTelefono = findViewById(R.id.edtTelefono);
        edtContrasena = findViewById(R.id.edtContrasena);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        txtIrLogin = findViewById(R.id.txtIrLogin);
        progressBar = findViewById(R.id.progressBar);

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
        String telefono = edtTelefono.getText().toString().trim();
        String contrasena = edtContrasena.getText().toString().trim();

        // --- Validaciones ---
        if (usuario.isEmpty()) {
            edtUsuario.setError("Ingrese un nombre de usuario");
            return;
        }

        if (telefono.isEmpty()) {
            edtTelefono.setError("Ingrese un número de teléfono");
            return;
        }

        if (!telefono.matches("6\\d{3}-\\d{4}")) {
            edtTelefono.setError("Formato inválido: 6XXX-XXXX");
            return;
        }

        if (contrasena.isEmpty()) {
            edtContrasena.setError("Ingrese una contraseña");
            return;
        }

        // Validación de contraseña: mínimo 10 caracteres, al menos mayúscula, minúscula, número y símbolo
        if (!contrasena.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{10,}$")) {
            edtContrasena.setError("Contraseña débil: mínimo 10 caracteres, con mayúscula, minúscula, número y símbolo");
            return;
        }

        // --- Mostrar loader ---
        progressBar.setVisibility(View.VISIBLE);
        btnRegistrarse.setEnabled(false);

        // --- Crear objeto Request ---
        RegisterRequest request = new RegisterRequest(usuario, contrasena, telefono);

        authApi.register(request).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                progressBar.setVisibility(View.GONE);
                btnRegistrarse.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(RegistroActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
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
