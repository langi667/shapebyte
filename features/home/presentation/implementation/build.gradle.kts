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

    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.home.presentation.contract)
            implementation(projects.core.di)
            implementation(projects.features.navigation)
            implementation(projects.features.featureToggles.domain)
            implementation(projects.core.logging)

            implementation(projects.foundation.core)
            implementation(projects.foundation.presentation.contract)
            implementation(projects.core.coroutines.contract)
            implementation(projects.core.logging.contract)
            implementation(projects.features.navigation.contract)
            implementation(projects.features.workout.data)
            implementation(projects.features.workout.domain)

            implementation(projects.features.featureToggles.domain.contract)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
        }

        commonTest.dependencies {
            // TODO: this must be solve differently, the test accesses implementation classes, there should be a test module passing these
            implementation(projects.features.workout.data)
            implementation(projects.features.workout.domain)

            implementation(libs.kotlin.test)
            implementation(projects.core.test)
        }

        androidUnitTest.dependencies {
            implementation(libs.mockk.android)
            implementation(projects.features.featureToggles.data.contract)
            implementation(projects.features.featureToggles.domain.contract)
        }
    }
}

android {
    namespace = "de.stefan.lang.shapebyte.features.home.presentation.implementation"
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
