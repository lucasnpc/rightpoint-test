plugins {
    id("com.android.library")
    id("kotlin-android")
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
    api(project(":data:core"))
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.preference)
    implementation(libs.kotlin.stdlib)
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    implementation(libs.javax.inject)
}
