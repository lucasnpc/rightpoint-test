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

    testOptions {
        kotlinOptions {
            // Turbine uses kotlinx.datetime underneath the hood
            freeCompilerArgs += listOf(
                "-Xopt-in=kotlin.time.ExperimentalTime"
            )
        }
    }
}

dependencies {
    api(project(":data:runtime"))
    api(project(":remote:injection"))
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.preference)
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.kotlin.stdlib)
}
