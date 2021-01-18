plugins {
    kotlin("multiplatform")
}

group = "com.scurab.mpp"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }
    sourceSets {
        val nativeMain by getting {
            dependencies {
                implementation(project(":dipho-shared"))
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}-native-mt")
                implementation("org.koin:koin-core:${Versions.koin}")
                implementation("io.ktor:ktor-client-core:${Versions.ktor}")
                implementation("io.ktor:ktor-client-serialization:${Versions.ktor}")
                implementation("io.ktor:ktor-client-curl:${Versions.ktor}")
            }
        }
        val nativeTest by getting
    }
}
