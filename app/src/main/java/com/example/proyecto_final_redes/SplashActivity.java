package com.example.proyecto_final_redes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 3000; // 2 segundos

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Verificar si ya vio el onboarding
                SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                boolean onboardingComplete = prefs.getBoolean("onboarding_complete", false);

                Intent intent;
                if (onboardingComplete) {
                    // Ya vio el onboarding, ir directo a MainActivity
                    intent = new Intent(SplashActivity.this, OnboardingActivity.class);
                } else {
                    // Primera vez, mostrar onboarding
                    intent = new Intent(SplashActivity.this, OnboardingActivity.class);
                }

                startActivity(intent);
                finish();
            }
        }, SPLASH_DURATION);

    }
}