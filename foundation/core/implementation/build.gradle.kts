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
            implementation(libs.kotlinx.coroutines.core)

            implementation(projects.core.di)
            implementation(projects.core.utils)
            implementation(projects.core.coroutines)

            implementation(projects.foundation.core.contract)
        }

        androidInstrumentedTest.dependencies {
            implementation(projects.core.test)
            implementation(projects.foundation.core)
            implementation(projects.foundation.core.fake)

            implementation(libs.androidx.test.ext.junit)
            implementation(libs.androidx.test.espresso.core)
            implementation(libs.junit.jupiter)
            implementation(libs.mockk.android)
            implementation(libs.koin.core)
            implementation(libs.koin.android)
            implementation (libs.koin.test)

        }

        androidUnitTest.dependencies {
            implementation(projects.core.test)
            implementation(libs.junit.jupiter)
            implementation(projects.foundation.core)
            implementation(projects.foundation.core.fake)
        }

        iosTest.dependencies {
            implementation(projects.core.test)

            implementation(projects.foundation.core)
            implementation(projects.foundation.core.fake)
            implementation(projects.foundation.core.implementation)
        }

    }
}

android {
    namespace = "de.stefan.lang.foundation.core.impl"
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