plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.exchangerates.features.favorites.usecases"
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

    implementation(project(":features:favorites:abstractions"))
    api(project(":features:favorites:entities"))
    api(project(":features:rates:entities"))

    implementation(libs.hilt.android)
    implementation(libs.androidx.room.common.jvm)
    ksp(libs.hilt.android.compiler)

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.gson)
    implementation(libs.retrofit.loggingInterceptor)

    implementation(libs.converter.gson)

    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}