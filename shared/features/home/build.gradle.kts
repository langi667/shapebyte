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

        androidUnitTest.dependencies {
            implementation(libs.mockk.android)
        }
        commonMain.dependencies {
            api(projects.shared.features.home.api)

            implementation(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)

            implementation(projects.shared.core)
            implementation(projects.shared.foundation)

            implementation(projects.shared.features.navigation)
            implementation(projects.shared.features.featureToggles)
            implementation(projects.shared.features.workout)
            implementation(projects.shared.features.home.presentation)
        }

        commonTest.dependencies {
            implementation(projects.shared.core.test)
            implementation(projects.shared.features.workout.data)
            implementation(projects.shared.features.workout.domain)
        }
    }
}

android {
    // TODO: set your module name
    namespace = "de.stefan.lang.shapebyte.features.home"
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