plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 31
    buildFeatures {
        buildConfig = false
    }
}

dependencies {
    implementation(project(":remote:core"))
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.timber)
}
