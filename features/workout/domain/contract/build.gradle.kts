plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.detekt)
    kotlin("plugin.serialization") version "2.0.0"
}

kotlin {
    explicitApi()
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
        commonMain.dependencies {
            implementation(projects.features.workout.data.contract)
            implementation(projects.core.utils)
            implementation(projects.core.logging.contract)
            implementation(projects.core.coroutines.contract)
            implementation(projects.foundation.core.contract)
            implementation(projects.features.featureToggles.data.contract)
            implementation(projects.features.featureToggles.domain.contract)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
        }

        commonTest.dependencies {
            implementation(projects.core.test)
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "de.stefan.lang.shapebyte.features.workout.domain.contract"
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
