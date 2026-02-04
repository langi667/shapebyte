import de.stefan.lang.di.configureDi

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.detekt)
}

configureDi(
    moduleClassName = "de.stefan.lang.shapebyte.features.workout.WorkoutPresentationModule",
    transitive = true,
)

kotlin {
    explicitApi()
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(projects.features.workout.presentation.contract)

            implementation(projects.features.workout.presentation.implementation)
            implementation(projects.features.workout.data.contract)
            implementation(projects.features.workout.domain)
            implementation(projects.foundation.core)

            implementation(projects.core.di)
            implementation(projects.core.logging)
            implementation(projects.core.coroutines)
            implementation(projects.foundation.presentation.contract)

            implementation(projects.features.featureToggles.data.contract)
            implementation(projects.features.featureToggles.domain.contract)
            implementation(projects.features.navigation.contract)
        }

        commonTest.dependencies {
            implementation(projects.core.test)
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.test.annotations.common)
        }
    }
}

android {
    namespace = "de.stefan.lang.shapebyte.features.workout.presentation"
    compileSdk = Project.Android.BuildSettings.targetSdk
    defaultConfig {
        minSdk = Project.Android.BuildSettings.minSdk
    }
    compileOptions {
        sourceCompatibility = Project.Android.BuildSettings.javaVersion
        targetCompatibility = Project.Android.BuildSettings.javaVersion
    }

    packaging {
        resources {
            excludes += Project.Android.BuildSettings.excludedResourcesList
        }
    }
}
