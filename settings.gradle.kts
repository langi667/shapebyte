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
include(":shared:core:test")
include(":shared:core:coroutines")
include(":shared:core:coroutines:api")
include(":shared:core:coroutines:impl")
include(":shared:core:coroutines:test")

include(":shared:foundation")
include(":shared:foundation:foundationCore")
include(":shared:foundation:foundationUI")

include(":shared:designsystem")
include(":shared:navigation")

include(":shared:features")
include(":shared:features:featureCore")
include(":shared:features:featureTest")

include(":shared:features:featureToggles")
include(":shared:features:featureToggles:featureTogglesData")
include(":shared:features:featureToggles:featureTogglesDomain")

include(":shared:features:home")
include(":shared:features:workout")
include(":shared:features:workout:workoutData")
include(":shared:features:workout:workoutDomain")


include(":shared:sharedComponentTest")
