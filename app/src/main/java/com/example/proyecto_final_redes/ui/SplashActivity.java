package com.example.proyecto_final_redes.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_final_redes.R;
import com.example.proyecto_final_redes.utils.AuthManager;
import com.example.proyecto_final_redes.utils.Constants;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AuthManager auth = new AuthManager(this);

        new Handler().postDelayed(() -> {

            // validar onboarding
            boolean onboardingComplete = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE)
                    .getBoolean(Constants.KEY_ONBOARDING, false);

            if (!onboardingComplete) {
                startActivity(new Intent(this, OnboardingActivity.class));
                finish();
                return;
            }

            // validar login
            if (auth.isLoggedIn()) {
                startActivity(new Intent(this, MainActivity.class));
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
            finish();

        }, SPLASH_DURATION);
    }
}
