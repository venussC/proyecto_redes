package com.example.proyecto_final_redes.ui;

import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.proyecto_final_redes.R;
import com.example.proyecto_final_redes.utils.AuthManager;

/**
 * Activity base que todas las demás activities deben extender
 * para tener el toolbar con navegación consistente
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected AuthManager authManager;

    /**
     * Configurar el toolbar común a todas las activities
     * Llamar este método DESPUÉS de setContentView()
     */
    protected void setupToolbar() {
        authManager = new AuthManager(this);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.mi_toolbar);
        if (toolbar == null) {
            Log.w("BaseActivity", "Toolbar no encontrado en el layout");
            return;
        }

        setSupportActionBar(toolbar);

        // Título "Inicio" → SIEMPRE regresar a MainActivity
        TextView txtInicio = toolbar.findViewById(R.id.txtInicio);
        if (txtInicio != null) {
            txtInicio.setOnClickListener(v -> navegarAInicio());
        }

        // Botón Usuario → Login o Perfil según estado
        ImageView btnUsuario = toolbar.findViewById(R.id.btnUsuario);
        if (btnUsuario != null) {
            btnUsuario.setOnClickListener(v -> navegarAUsuario());
        }
    }

    /**
     * Navegar a la pantalla de inicio (MainActivity)
     */
    private void navegarAInicio() {
        // Si ya estamos en MainActivity, no hacer nada
        if (getClass().getSimpleName().equals("MainActivity")) {
            return;
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    /**
     * Navegar al perfil de usuario o login según estado
     */
    private void navegarAUsuario() {
        Log.d("BaseActivity", "Click en botón usuario. Logueado: " + authManager.isLoggedIn());

        String currentActivity = getClass().getSimpleName();

        if (authManager.isLoggedIn()) {
            // Usuario logueado → Ir a TurnoConfirmado
            // Si ya estamos ahí, no hacer nada
            if (currentActivity.equals("TurnoConfirmado")) {
                return;
            }
            startActivity(new Intent(this, TurnoConfirmado.class));
        } else {
            // Usuario NO logueado → Ir a Login
            // Si ya estamos ahí, no hacer nada
            if (currentActivity.equals("LoginActivity")) {
                return;
            }
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}