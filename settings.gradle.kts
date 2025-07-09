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
include(":shared:core:coroutines")
include(":shared:core:coroutines:coroutinesProviding")
include(":shared:core:coroutines:coroutinesProvidingTest")

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
