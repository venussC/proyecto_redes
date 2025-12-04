package com.example.proyecto_final_redes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_onboarding);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewPager = findViewById(R.id.viewPagerOnboarding);
        dotsIndicator = findViewById(R.id.dotsIndicator);
        btnSkip = findViewById(R.id.btnSkip);
        btnNext = findViewById(R.id.btnNext);

        // Configuracion para pasar los datos al adapter
        adapter = new OnboardingAdapter(images, titulos, descripciones);
        viewPager.setAdapter(adapter);

        // Conectar TabLayout con ViewPager2 (esto crea los dots automáticamente) supuestamente
        new TabLayoutMediator(dotsIndicator, viewPager,
                (tab, position) -> {

                }
                ).attach();

        //Listeners para los cambios de pagina
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

                // Para cambiar el texto del boton en la ultima pagina
                if (position == images.length - 1) {
                    btnNext.setText("Empezar ahora");
                    btnSkip.setVisibility(View.GONE);
                } else {
                    btnNext.setText("Siguiente");
                    btnSkip.setVisibility(View.VISIBLE);
                }
            }
        });

        // Boton skip
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishOnborading();
            }
        });

        // Boton next
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() == images.length - 1 ) {
                    finishOnborading();
                } else {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
            }
        });
    }

    private void finishOnborading() {
        // Guardamos en sharedreferences que ya vio el onboarding
        getSharedPreferences("AppPrefs", MODE_PRIVATE)
                .edit()
                .putBoolean("onboarding_complete", true)
                .apply();

        // Redireccion al mainactivity o home
        Intent intent = new Intent(OnboardingActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}