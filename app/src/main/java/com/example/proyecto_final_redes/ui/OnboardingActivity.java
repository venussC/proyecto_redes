package com.example.proyecto_final_redes.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.proyecto_final_redes.R;
import com.example.proyecto_final_redes.utils.Constants;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout dotsIndicator;
    private Button btnSkip, btnNext;
    private OnboardingAdapter adapter;

    // DATOS PARA LAS 3 PANTALLAS DE ONBOARDING
    private int[] images = {
            R.drawable.saladeespera,
            R.drawable.onb2,
            R.drawable.onb3,
    };

    private String[] titulos = {
            "Sala de espera",
            "Turno virtual",
            "Notificaciones",
    };

    private String[] descripciones = {
            "¿Cansado de largas esperas en la clínica?",
            "Solicita tu turno desde tu celular y ve el tiempo real",
            "Te avisamos cuando se acerque tu turno. ¡Usa tu tiempo!",
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_onboarding);

        viewPager = findViewById(R.id.viewPagerOnboarding);
        dotsIndicator = findViewById(R.id.dotsIndicator);
        btnSkip = findViewById(R.id.btnSkip);
        btnNext = findViewById(R.id.btnNext);

        // Configurar datos
        adapter = new OnboardingAdapter(images, titulos, descripciones);
        viewPager.setAdapter(adapter);

        // Dots indicator
        new TabLayoutMediator(dotsIndicator, viewPager, (tab, position) -> { }).attach();

        // Cambiar texto del botón en la última página
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == images.length - 1) {
                    btnNext.setText("Empezar ahora");
                    btnSkip.setVisibility(View.GONE);
                } else {
                    btnNext.setText("Siguiente");
                    btnSkip.setVisibility(View.VISIBLE);
                }
            }
        });

        // Botón Skip
        btnSkip.setOnClickListener(v -> finishOnboarding());

        // Botón Next
        btnNext.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() == images.length - 1) {
                finishOnboarding();
            } else {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });
    }

    private void finishOnboarding() {
        getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE)
                .edit()
                .putBoolean(Constants.KEY_ONBOARDING, true)
                .apply();

        startActivity(new Intent(this, LoginActivity.class));
        finish();
        startActivity(new Intent(OnboardingActivity.this, LoginActivity.class));
        finish();
    }

}
