import java.lang.System.getProperty

apply {
    from(rootProject.file("gradle/spotless-config.gradle"))
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter {
            content {
                includeModule("org.jetbrains.trove4j", "trove4j")
                includeModule("org.jetbrains.kotlinx", "kotlinx-html-jvm")
            }
        }
    }

    apply(plugin = "com.github.ben-manes.versions")
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    }

    dependencies {
        classpath(libs.android.gradle)
        classpath(libs.kotlin.gradle)
        classpath(libs.versioning.gradle)
        classpath(libs.hilt.gradle)
        classpath(libs.spotless.gradle)
        classpath(libs.androidx.navigation.safeargs.gradle)
    }
}

fun propOrEmpty(name: String): String {
    return if (hasProperty(name)) getProperty(name) else ""
}
