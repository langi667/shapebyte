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

            implementation(projects.core)
            implementation(projects.foundation)
            implementation(projects.designsystem)
            implementation(projects.features.featureToggles)
            implementation(projects.features.navigation)
            implementation(projects.features.workout.api)
            implementation(projects.features.workout.domain)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.turbine)
            implementation(libs.kotlinx.coroutines.test)
            implementation (libs.koin.test)

            implementation (projects.core.test)
            implementation (projects.foundation.core.test)
            implementation (projects.foundation.core.test)
        }

        androidUnitTest.dependencies {

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