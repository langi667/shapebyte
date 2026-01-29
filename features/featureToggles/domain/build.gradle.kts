import de.stefan.lang.build.di.configureDi

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.detekt)
    kotlin("plugin.serialization") version "2.0.0"
}

configureDi(moduleClassName = "de.stefan.lang.shapebyte.featureTogglesDomain.FeatureTogglesDomainModule")

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
            api(projects.features.featureToggles.domain.contract)

            implementation(projects.features.featureToggles.domain.implementation)

            implementation(projects.core.logging)
            implementation(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            implementation(projects.core.di)
            implementation(projects.core.utils)
            implementation(projects.core.coroutines)
            implementation(projects.features.featureToggles.data)
        }

        commonTest.dependencies {
            implementation(projects.core.test)
        }

    }
}

android {
    namespace = "de.stefan.lang.shapebyte.featureTogglesDomain"
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
