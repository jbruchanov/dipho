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

    val mingwPath = File(System.getenv("MINGW64_DIR") ?: "c:/Utils/Windozy/msys64/mingw64")
    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "main"
                if (isMingwX64) {
                    /*
                         this needs to have installed https://www.msys2.org/
                         and in the msys2 terminal install `mingw-w64-x86_64-curl`
                         pacman -S mingw-w64-x86_64-curl
                     */
                    // Add lib path to `libcurl` and its dependencies:
                    linkerOpts("-L${mingwPath.resolve("lib")}")
                    runTask?.environment("PATH" to mingwPath.resolve("bin"))
                }
                runTask?.args("https://www.je   tbrains.com/")
            }
        }
    }

    sourceSets {
        val nativeMain by getting {
            dependencies {
                implementation(project(":dipho-shared"))
                implementation("org.koin:koin-core:${Versions.koin}")
                implementation("io.ktor:ktor-client-core:${Versions.ktor}")
                implementation("io.ktor:ktor-client-serialization:${Versions.ktor}")
                implementation("io.ktor:ktor-client-curl:${Versions.ktor}")
            }
        }
        val nativeTest by getting
    }
}
