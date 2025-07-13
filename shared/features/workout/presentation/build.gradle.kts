plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.detekt)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = Project.Android.BuildSettings.javaVersion.toString()
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets  {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.koin.core)

            implementation(projects.shared.core)
            implementation(projects.shared.foundation)
            implementation(projects.shared.designsystem)
            implementation(projects.shared.features.featureToggles)
            implementation(projects.shared.features.navigation)
            implementation(projects.shared.features.workout.api)
            implementation(projects.shared.features.workout.domain)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.turbine)
            implementation(libs.kotlinx.coroutines.test)
            implementation (libs.koin.test)

            implementation (projects.shared.core.test)
            implementation (projects.shared.foundation.core.test)
            implementation (projects.shared.foundation.core.test)
        }

        androidUnitTest.dependencies {
            implementation (projects.shared.features.test)
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

    sourceSets {
        defaultConfig {
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }
}