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
include(":shared:core:coreUtils")
include(":shared:core:coreTest")
include(":shared:testcore")
include(":shared:foundation")
include(":shared:foundation:foundationCore")
include(":shared:foundation:foundationUI")
