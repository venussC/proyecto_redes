plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.proyecto_final_redes"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.proyecto_final_redes"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.cardview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // CardView y ViewPager2
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    // Retrofit para llamadas HTTP
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp para logging
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // Gson para JSON
    implementation("com.google.code.gson:gson:2.10.1")

    // Para SharedPreferences encriptadas (guardar token)
    implementation("androidx.security:security-crypto:1.1.0-alpha06")
}