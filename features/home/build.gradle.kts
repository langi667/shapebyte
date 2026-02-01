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
            implementation(projects.features.featureToggles.data.contract)
            implementation(projects.features.featureToggles.domain.contract)
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

            implementation(projects.features.navigation)
            implementation(projects.features.featureToggles.data)
            implementation(projects.features.featureToggles.domain)
            api(projects.features.home.presentation.contract)
            implementation(projects.features.home.presentation)
            implementation(projects.features.workout.domain)
        }

        commonTest.dependencies {
            implementation(projects.core.test)
            implementation(projects.features.workout.data)
            implementation(projects.features.workout.data.contract)

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
