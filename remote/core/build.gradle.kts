plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = 31
    buildFeatures {
        buildConfig = false
    }
}

dependencies {
    implementation(libs.kotlin.stdlib)
    api(libs.okhttp.runtime)
    api(libs.okhttp.logging)
    api(libs.retrofit.runtime)
    api(libs.retrofit.moshi)
    implementation(libs.moshi.runtime)
    kapt(libs.moshi.codegen)
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    testImplementation(libs.androidx.test.junit)
    testImplementation(libs.androidx.test.truth)
}
