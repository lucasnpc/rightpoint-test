include(
    ":app",
    ":core",
    ":data:core",
    ":data:runtime",
    ":data:injection",
    ":remote:core",
    ":remote:injection"
)

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

enableFeaturePreview("VERSION_CATALOGS")
