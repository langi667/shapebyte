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
include(":foundation:ui")
include(":foundation:ui:api")

include(":designsystem")
include(":designsystem:api")

include(":shared:features")
include(":shared:features:navigation")
include(":shared:features:navigation:api")

include(":shared:features:featureToggles")
include(":shared:features:featureToggles:api")

include(":shared:features:featureToggles:data")
include(":shared:features:featureToggles:domain")

include(":shared:features:home")
include(":shared:features:home:api")
include(":shared:features:home:presentation")

include(":shared:features:workout")
include(":shared:features:workout:api")
include(":shared:features:workout:data")
include(":shared:features:workout:domain")
include(":shared:features:workout:presentation")


include(":shared:sharedComponentTest")
