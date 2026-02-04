plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.detekt)
    kotlin("plugin.serialization") version "2.0.0"
}

kotlin {
    explicitApi()
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(projects.core.coroutines)
            implementation(projects.core.logging)
            implementation(projects.core.di)

            implementation(projects.foundation.core)
            implementation(projects.core.logging)
            implementation(projects.features.featureToggles.domain.contract)
            implementation(projects.features.featureToggles.data)
        }

        commonTest.dependencies {
            implementation(projects.core.test)
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.test.annotations.common)
        }

        androidUnitTest.dependencies {
            implementation(libs.mockk.android)
            implementation(projects.core.test)
            implementation(libs.junit.jupiter)
            implementation(projects.foundation.core)
            implementation(projects.foundation.core.fake)
        }
    }
}

android {
    namespace = "de.stefan.lang.shapebyte.featureToggles.domain.implementation"
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
