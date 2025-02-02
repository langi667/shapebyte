enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ShapeByte"
include(":androidApp")
include(":shared")
include(":shared:core")
include(":shared:core:test")
include(":shared:testcore")
include(":shared:foundation")
include(":shared:foundation:base")
include(":shared:foundation:ui")
