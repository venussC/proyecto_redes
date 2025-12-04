package com.example.proyecto_final_redes.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_final_redes.R;
import com.example.proyecto_final_redes.api.AuthApi;
import com.example.proyecto_final_redes.network.RetrofitClient;
import com.example.proyecto_final_redes.utils.AuthManager;
import com.example.proyecto_final_redes.models.LoginRequest;
import com.example.proyecto_final_redes.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvIrRegistro;
    private ProgressBar progressBar;

    private AuthApi authApi;
    private AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar API y manager
        authApi = RetrofitClient.getInstance(this).getAuthApi();
        authManager = new AuthManager(this);

        // Referencias XML
        etUsername = findViewById(R.id.edtUsuario);
        etPassword = findViewById(R.id.edtContrasena);
        btnLogin = findViewById(R.id.btnIniciarSesion);
        tvIrRegistro = findViewById(R.id.txtRegistrar);
        progressBar = findViewById(R.id.progressBar);

        // Botón login
        btnLogin.setOnClickListener(v -> realizarLogin());

        // Ir a registro
        tvIrRegistro.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegistroActivity.class)));
    }

    private void realizarLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validaciones
        if (username.isEmpty()) {
            etUsername.setError("Ingrese su usuario");
            return;
        }
        if (password.isEmpty()) {
            etPassword.setError("Ingrese su contraseña");
            return;
        }

        // Mostrar loader
        progressBar.setVisibility(View.VISIBLE);
        btnLogin.setEnabled(false);

        LoginRequest request = new LoginRequest(username, password);

        Call<LoginResponse> call = authApi.login(request);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                progressBar.setVisibility(View.GONE);
                btnLogin.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {

                    // Guardar tokens
                    authManager.saveTokens(
                            response.body().getAccessToken(),
                            response.body().getRefreshToken()
                    );

                    Toast.makeText(LoginActivity.this, "Inicio exitoso", Toast.LENGTH_SHORT).show();

                    // Ir al dashboard
                    Intent intent = new Intent(LoginActivity.this, dashboardadminActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                btnLogin.setEnabled(true);
                Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
