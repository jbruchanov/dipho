plugins {
    kotlin("js")
}

group = "com.scurab.dipho.web"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven { url = uri("https://dl.bintray.com/kotlin/kotlin-js-wrappers") }
    maven { url = uri("https://dl.bintray.com/ekito/koin") }
}

dependencies {
    implementation(project(":core"))

    //implementation("org.koin:koin-core:${Versions.koin}")

    implementation("org.jetbrains:kotlin-react:${Versions.reactJs}")
    implementation("org.jetbrains:kotlin-react-dom:${Versions.reactJs}")
    implementation("org.jetbrains:kotlin-react-router-dom:5.2.0-pre.134-kotlin-${Versions.kotlin}")
    implementation("org.jetbrains", "kotlin-styled", "5.2.0-pre.134-kotlin-${Versions.kotlin}")

    testImplementation(kotlin("test-js"))
}

kotlin {
    js(LEGACY) {
        browser {
            binaries.executable()
            webpackTask {
                cssSupport.enabled = true
            }
            runTask {
                //devServer = devServer?.copy(port = 3030)
                cssSupport.enabled = true
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
    }
}