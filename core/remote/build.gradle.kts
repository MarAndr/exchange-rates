plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.example.exchangerates.core.remote"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
        targetSdk = 35
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":core:loading"))

    implementation(libs.retrofit)
    implementation(libs.squareup.converter.moshi)
    implementation(libs.retrofit.loggingInterceptor)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    implementation(libs.gson)

    implementation(libs.converter.gson)
}