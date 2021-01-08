plugins {
    id("com.android.application")
    kotlin("android")
}

group = "com.scurab.dipho.android"
version = "1.0-SNAPSHOT"

repositories {
    google()
    jcenter()
}

dependencies {
    implementation(project(":core"))
    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.appcompat:appcompat:1.2.0")
}

android {
    compileSdkVersion(29)
    defaultConfig {
        applicationId = "com.scurab.dipho.android"
        minSdkVersion(24)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures.viewBinding = true
}