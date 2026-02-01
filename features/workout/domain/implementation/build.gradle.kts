import de.stefan.lang.di.configureDi

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
            implementation(projects.features.featureToggles.data.contract)
            implementation(projects.features.featureToggles.domain.contract)
            implementation(projects.features.workout.data)
            implementation(projects.features.workout.domain.contract)
        }

        commonTest.dependencies {
            implementation(projects.core.test)
        }

        androidUnitTest.dependencies {
            implementation(libs.mockk.android)
            implementation(projects.core.test)
            implementation(projects.features.workout.domain.test)
        }
    }
}

android {
    namespace = "de.stefan.lang.shapebyte.features.workout.domain"
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
