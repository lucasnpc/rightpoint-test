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
    api(project(":remote:core"))
    implementation(libs.androidx.annotation)
    implementation(libs.kotlin.stdlib)
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    testImplementation(libs.androidx.test.junit)
    testImplementation(libs.androidx.test.truth)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine) {
        because("it provides a fluent API for testing kotlinx.coroutine.Flow")
    }
}
