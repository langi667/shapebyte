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
        commonMain.dependencies {
            implementation(projects.features.featureToggles.data.contract)
            implementation(projects.core.di)
            implementation(projects.foundation.core)
            implementation(projects.core.logging)
            implementation(projects.core.coroutines)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
        }

        commonTest.dependencies {
            implementation(projects.core.test)
            implementation(projects.foundation.core.fake)
            implementation(libs.kotlinx.coroutines.test)

            implementation(libs.kotlin.test)
            implementation(libs.kotlin.test.annotations.common)
        }

        androidUnitTest.dependencies {
            implementation(libs.kotlin.test.annotations.common)
        }
    }
}

android {
    namespace = "de.stefan.lang.shapebyte.featureToggles.data.implementation"
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
