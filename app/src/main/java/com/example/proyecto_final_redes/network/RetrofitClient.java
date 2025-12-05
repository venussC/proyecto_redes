package com.example.proyecto_final_redes.network;

import android.content.Context;

import com.example.proyecto_final_redes.api.AuthApi;
import com.example.proyecto_final_redes.api.ClinicApi;
import com.example.proyecto_final_redes.api.SpecialityApi;
import com.example.proyecto_final_redes.api.TurnApi;
import com.example.proyecto_final_redes.api.PatientApi;
import com.example.proyecto_final_redes.api.TurnStatusApi;

import com.example.proyecto_final_redes.utils.AuthManager;
import com.example.proyecto_final_redes.utils.Constants;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class RetrofitClient {

    private static RetrofitClient instance;
    private final Retrofit retrofit;
    private final AuthManager authManager;

    private RetrofitClient(Context context) {

        // Usamos ApplicationContext para evitar memory leaks
        authManager = new AuthManager(context.getApplicationContext());

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(chain -> {

                    Request original = chain.request();
                    Request.Builder builder = original.newBuilder()
                            .header("Content-Type", "application/json");

                    // Agregar token si existe
                    String token = authManager.getAccessToken();
                    if (token != null && !token.isEmpty()) {
                        builder.header("Authorization", "Bearer " + token);
                    }

                    return chain.proceed(builder.build());
                })
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    // 🔥 Método correcto usando Context
    public static synchronized RetrofitClient getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitClient(context);
        }
        return instance;
    }

    public static synchronized void resetInstance() {
        instance = null;
    }


    // APIS
    public AuthApi getAuthApi() { return retrofit.create(AuthApi.class); }
    public SpecialityApi getSpecialityApi() { return retrofit.create(SpecialityApi.class); }
    public TurnApi getTurnApi() { return retrofit.create(TurnApi.class); }
    public PatientApi getPatientApi() { return retrofit.create(PatientApi.class); }
    public TurnStatusApi getTurnStatusApi() { return retrofit.create(TurnStatusApi.class); }
    public ClinicApi getClinicApi() { return retrofit.create(ClinicApi.class); }

}
