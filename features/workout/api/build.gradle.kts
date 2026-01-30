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
            implementation(projects.core.di)
            implementation(projects.core.utils)
            implementation(projects.core.coroutines)
            
            implementation(projects.foundation.core)
            implementation(projects.foundation.presentation)
            implementation(projects.foundation.presentation.contract)
            implementation(projects.designsystem)

            implementation(projects.features.featureToggles.data.contract)
            implementation(projects.features.featureToggles.domain.contract)
            implementation(projects.features.navigation)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.turbine)
            implementation(libs.kotlinx.coroutines.test)
            implementation (libs.koin.test)

            implementation (projects.core.test)
            implementation (projects.foundation.core.fake)
            implementation (projects.foundation.core.fake)
        }

        androidUnitTest.dependencies {
        }
    }
}

android {
    namespace = "de.stefan.lang.shapebyte.features.workout.api"
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
