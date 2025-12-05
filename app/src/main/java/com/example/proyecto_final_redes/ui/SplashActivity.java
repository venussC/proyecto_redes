package com.example.proyecto_final_redes.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.example.proyecto_final_redes.R;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DURATION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("FLOW", "Splash onCreate");
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            Log.d("FLOW", "Splash -> abrir Onboarding (forzado temporal)");
            startActivity(new Intent(SplashActivity.this, OnboardingActivity.class));
            finish();
        }, SPLASH_DURATION);
    }
}

