import com.android.build.api.variant.BuildConfigField

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "com.challenge.whatswrong"
        minSdk = 23
        targetSdk = 31
        buildToolsVersion = "30.0.2"
        versionCode = 1
        versionName = "1.0.0"
        testApplicationId = "com.challenge.whatswrong.test"
    }

    signingConfigs {
        findByName("debug")?.apply {
            storeFile = file("debug.keystore")
            storePassword = "debugAndroid"
            keyAlias = "debug"
            keyPassword = "debugAndroid"
        }

        if (file("release.keystore").exists()) {
            create("release") {
                storeFile = file("release.keystore")
                storePassword = ""
                keyAlias = "template"
                keyPassword = ""
            }
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("debug")
        }
        getByName("release") {
            signingConfig = if (file("release.keystore").exists()) {
                signingConfigs.getByName("release")
            } else {
                signingConfigs.getByName("debug")
            }
        }
    }

    packagingOptions {
        excludes.add("META-INF/*.kotlin_module")
    }

    buildFeatures.viewBinding = true

    lint {
        isCheckDependencies = true
        isIgnoreTestSources = true
        isAbortOnError = true
        xmlReport = true
        xmlOutput = File("${project.buildDir}/reports/lint/lint-result.xml")
        htmlReport = true
        htmlOutput = File("${project.buildDir}/reports/lint/lint-result.html")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    testOptions.reportDir = "${project.rootDir}/reports/${project.displayName}"
}

fun gitSha(): String {
    return cachedBuildFile(filename = "commit-sha.txt", "git", "rev-parse", "HEAD")
}

fun gitTimestamp(): String {
    return cachedBuildFile(
        filename = "commit-timestamp.txt",
        "git", "log", "-n", "1", "--format=%at"
    )
}

fun cachedBuildFile(filename: String, vararg command: String): String {
    val f = File(buildDir, filename)
    if (!f.exists()) {
        val p = ProcessBuilder(*command).directory(project.rootDir).start()
        if (p.waitFor() != 0) throw RuntimeException(p.errorStream.reader().readText())
        f.parentFile.mkdirs()
        f.outputStream().use { outputStream -> p.inputStream.copyTo(outputStream) }
    }
    return f.readText().trim()
}

androidComponents {
    onVariants {
        it.buildConfigFields.put(
            "COMMIT_SHA",
            BuildConfigField("String", "\"${gitSha()}\"", null)
        )
        it.buildConfigFields.put(
            "COMMIT_UNIX_TIMESTAMP",
            BuildConfigField("Long", "${gitTimestamp()}L", null)
        )
    }
}

dependencies {
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.espresso.contrib)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.junit)

    implementation(project(":core"))
    implementation(project(":data:injection"))
    implementation(files("libs/lint-rules-release.aar"))
    implementation(libs.kotlin.stdlib)
    implementation(libs.ticktock)
    implementation(libs.androidx.core)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.lifecycle.common)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.navigation.common)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.runtime)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.material.components)
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    implementation(libs.timber)
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    debugImplementation(libs.leakcanary)
    coreLibraryDesugaring(libs.desugar.jdk)

    testImplementation(libs.androidx.test.junit)
    testImplementation(libs.androidx.test.truth)
}
