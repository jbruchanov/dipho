buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.21")
        classpath("com.android.tools.build:gradle:4.0.2")
    }
}

group = "com.scurab.mpp"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }
}