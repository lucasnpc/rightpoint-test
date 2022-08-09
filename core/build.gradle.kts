plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = 31
    buildFeatures {
        viewBinding = true
        buildConfig = false
    }
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.lifecycle.common)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.material.components)
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    implementation(libs.timber)
}
