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
            api(projects.features.workout.api)

            implementation(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)

            implementation(projects.features.workout.api)
            implementation(projects.features.workout.data)
            implementation(projects.features.workout.domain)
            implementation(projects.features.workout.presentation)

            implementation(projects.core)
            implementation(projects.foundation)
            implementation(projects.designsystem)
            implementation(projects.features.navigation)
            implementation(projects.features.featureToggles)
        }

        androidUnitTest.dependencies {
            implementation(libs.mockk.android)
        }

        commonTest.dependencies {
            implementation(projects.core.test)
        }
    }
}

android {
    // TODO: set your module name
    namespace = "de.stefan.lang.shapebyte.features.workout"
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

