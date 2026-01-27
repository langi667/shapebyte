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
include(":core")
include(":core:di")

include(":core:utils")
include(":core:logging")
include(":core:logging:contract")
include(":core:logging:implementation")
include(":core:logging:fake")

include(":core:test")
include(":core:coroutines")
include(":core:coroutines:contract")
include(":core:coroutines:implementation")
include(":core:coroutines:test")

include(":foundation")
include(":foundation:core")
include(":foundation:core:contract")
include(":foundation:core:implementation")
include(":foundation:core:fake")
include(":foundation:presentation")
include(":foundation:presentation")

include(":designsystem")

include(":features")
include(":features:navigation")
include(":features:navigation:api")

include(":features:featureToggles")
include(":features:featureToggles:api")

include(":features:featureToggles:data")
include(":features:featureToggles:domain")

include(":features:home")
include(":features:home:presentation")

include(":features:workout")
include(":features:workout:api")
include(":features:workout:data")
include(":features:workout:domain")
include(":features:workout:presentation")

include(":shared:sharedComponentTest")
