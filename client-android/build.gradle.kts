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
    implementation("androidx.fragment:fragment-ktx:${Versions.androidFragment}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidLifecycle}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Versions.androidLifecycle}")

    // Koin for Android
    implementation("org.koin:koin-android:${Versions.koin}")
    implementation("org.koin:koin-androidx-scope:${Versions.koin}")
    implementation("org.koin:koin-androidx-viewmodel:${Versions.koin}")
    implementation("org.koin:koin-androidx-fragment:${Versions.koin}")

    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.appcompat:appcompat:${Versions.androidAppCompat}")
}

android {
    compileSdkVersion(30)
    defaultConfig {
        applicationId = "com.scurab.dipho.android"
        minSdkVersion(24)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures.viewBinding = true

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}