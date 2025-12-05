package com.example.proyecto_final_redes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_final_redes.R;
import com.example.proyecto_final_redes.api.AuthApi;
import com.example.proyecto_final_redes.models.LoginRequest;
import com.example.proyecto_final_redes.models.LoginResponse;
import com.example.proyecto_final_redes.network.RetrofitClient;
import com.example.proyecto_final_redes.utils.AuthManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsuario, etContrasena;
    private Button btnLogin, btnSaltarLogin;
    private TextView tvIrRegistro;
    private ProgressBar progressBar;

    private AuthApi authApi;
    private AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authManager = new AuthManager(this);

        // Validar sesión antes de inflar layout
        if (authManager.isLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        // Inicializar API
        authApi = RetrofitClient.getInstance(this).getAuthApi();

        // Referencias XML
        etUsuario = findViewById(R.id.edtUsuario);
        etContrasena = findViewById(R.id.edtContrasena);
        btnLogin = findViewById(R.id.btnIniciarSesion);
        btnSaltarLogin = findViewById(R.id.btnSaltarLogin);
        tvIrRegistro = findViewById(R.id.txtRegistrar);
        progressBar = findViewById(R.id.progressBar);

        // Click listeners
        btnLogin.setOnClickListener(v -> realizarLogin());
        tvIrRegistro.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegistroActivity.class))
        );
        btnSaltarLogin.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        });
    }

    private void realizarLogin() {
        String usuario = etUsuario.getText().toString().trim();
        String contrasena = etContrasena.getText().toString().trim();

        if (usuario.isEmpty()) {
            etUsuario.setError("Ingrese su usuario");
            return;
        }
        if (contrasena.isEmpty()) {
            etContrasena.setError("Ingrese su contraseña");
            return;
        }

        progressBar.setVisibility(ProgressBar.VISIBLE);
        btnLogin.setEnabled(false);

        LoginRequest request = new LoginRequest(usuario, contrasena);
        authApi.login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressBar.setVisibility(ProgressBar.GONE);
                btnLogin.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();

                    // Guardar tokens
                    authManager.saveTokens(
                            loginResponse.getAccessToken(),
                            loginResponse.getRefreshToken()
                    );

                    // Reiniciar retrofit con token ya guardado
                    RetrofitClient.resetInstance();
                    RetrofitClient.getInstance(getApplicationContext());

                    // 👈 recrear authApi con el nuevo retrofit
                    authApi = RetrofitClient.getInstance(getApplicationContext()).getAuthApi();

                    // Guardar info del usuario
                    authManager.saveUserInfo(
                            -1L,
                            null,
                            usuario
                    );

                    Log.d("LoginActivity", "Login exitoso. Token y datos guardados.");

                    Toast.makeText(LoginActivity.this, "Inicio exitoso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressBar.setVisibility(ProgressBar.GONE);
                btnLogin.setEnabled(true);
                Toast.makeText(LoginActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
