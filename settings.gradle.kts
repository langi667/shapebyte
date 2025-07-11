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
include(":shared:core:di")

include(":shared:core:utils")
include(":shared:core:utils:api")
include(":shared:core:utils:impl")
include(":shared:core:utils:test")

include(":shared:core:test")
include(":shared:core:coroutines")
include(":shared:core:coroutines:api")
include(":shared:core:coroutines:impl")
include(":shared:core:coroutines:test")

include(":shared:foundation")
include(":shared:foundation:core")
include(":shared:foundation:core:api")
include(":shared:foundation:core:impl")
include(":shared:foundation:core:test")
include(":shared:foundation:ui")
include(":shared:foundation:ui:api")

include(":shared:designsystem")
include(":shared:designsystem:api")

include(":shared:features")
include(":shared:features:test")
include(":shared:features:navigation")
include(":shared:features:featureToggles")
include(":shared:features:featureToggles:api")

include(":shared:features:featureToggles:data")
include(":shared:features:featureToggles:featureTogglesDomain")

include(":shared:features:home")
include(":shared:features:workout")
include(":shared:features:workout:workoutData")
include(":shared:features:workout:workoutDomain")


include(":shared:sharedComponentTest")
