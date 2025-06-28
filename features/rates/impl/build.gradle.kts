plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.exchangerates.features.rates.impl"
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
    implementation(project(":core:remote"))
    api(project(":features:rates:api"))

    implementation(libs.retrofit)
    implementation(libs.squareup.converter.moshi)
    implementation(libs.retrofit.loggingInterceptor)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    implementation(libs.gson)

    implementation(libs.converter.gson)

    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}