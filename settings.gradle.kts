pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        mavenCentral()
    }
    
}
rootProject.name = "dipho"

include(":client-android")
include(":client-web")
include(":client-native")
include(":dipho-shared")

