pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        mavenCentral()
    }
    
}
rootProject.name = "dipho"

include(":core")
include(":client-android")
include(":client-web")

