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
include(":core:utils:api")
include(":core:utils:impl")
include(":core:utils:test")

include(":core:test")
include(":core:coroutines")
include(":core:coroutines:api")
include(":core:coroutines:impl")
include(":core:coroutines:test")

include(":foundation")
include(":foundation:core")
include(":foundation:core:api")
include(":foundation:core:impl")
include(":foundation:core:test")
include(":foundation:presentation")
include(":foundation:presentation")
include(":foundation:presentation:api")

include(":designsystem")
include(":designsystem:api")

include(":features")
include(":features:navigation")
include(":features:navigation:api")

include(":features:featureToggles")
include(":features:featureToggles:api")

include(":features:featureToggles:data")
include(":features:featureToggles:domain")

include(":features:home")
include(":features:home:api")
include(":features:home:presentation")

include(":features:workout")
include(":features:workout:api")
include(":features:workout:data")
include(":features:workout:domain")
include(":features:workout:presentation")


include(":shared:sharedComponentTest")
