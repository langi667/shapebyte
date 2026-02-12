plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.detekt)
}

kotlin {
    explicitApi()
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.workout.presentation.contract)

            implementation(projects.core.di)
            implementation(projects.core.utils.contract)
            implementation(projects.core.coroutines.contract)
            implementation(projects.core.logging.contract)
            implementation(projects.foundation.core.contract)
            implementation(projects.foundation.presentation.contract)

            implementation(projects.designsystem.contract)
            implementation(projects.features.navigation.contract)

            implementation(projects.features.workout.data.contract)
            implementation(projects.features.workout.domain.contract)
            implementation(projects.features.featureToggles.domain.contract)

            implementation(libs.kotlinx.coroutines.core)
            
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(projects.core.test)
        }

        androidUnitTest.dependencies {
            implementation(libs.mockk.android)
            implementation(libs.turbine)
            implementation(libs.kotlinx.coroutines.test)

            implementation(projects.core.test)
            implementation(projects.features.featureToggles.data.contract)
            implementation(projects.features.featureToggles.domain.contract)
            implementation(projects.features.workout.data)
            implementation(projects.features.workout.data.contract)
            implementation(projects.features.workout.domain)
            implementation(projects.features.workout.presentation.test)
        }
    }
}

android {
    namespace = "de.stefan.lang.shapebyte.features.workout.presentation.implementation"
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
