plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.detekt)
    kotlin("plugin.serialization") version "2.0.0"
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

    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(projects.core.di)
            implementation(projects.core.utils)
            implementation(projects.core.coroutines)
            implementation(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)

            
            implementation(projects.foundation.core)
            implementation(projects.foundation.presentation)
            implementation(projects.designsystem)
            implementation(projects.features.featureToggles)

            implementation(projects.features.workout.api)
            implementation(projects.features.workout.data)
        }

        commonTest.dependencies {
            implementation(projects.core.test)
        }

        androidUnitTest.dependencies {
            implementation(libs.mockk.android)
        }
    }
}

android {
    // TODO: set your module name
    namespace = "de.stefan.lang.shapebyte.features.workout.workoutDomain"
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
