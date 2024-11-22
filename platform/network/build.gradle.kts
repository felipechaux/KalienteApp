plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.network"
    compileSdk = 34

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.network.response.adapter)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.retrofit.core)
    implementation(libs.kotlin.serialization.core)
    implementation(libs.gson)
    implementation(libs.converter.gson)
    api(libs.retrofit.core)
    api(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.compiler)
}