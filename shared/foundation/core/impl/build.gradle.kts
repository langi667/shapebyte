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

            implementation(projects.core)
            implementation(projects.shared.foundation.core.api)
        }

        androidInstrumentedTest.dependencies {
            implementation(projects.core.test)
            implementation(projects.shared.foundation.core)
            implementation(projects.shared.foundation.core.test)

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
            implementation(projects.shared.foundation.core)
            implementation(projects.shared.foundation.core.test)
        }

        iosTest.dependencies {
            implementation(projects.core.test)

            implementation(projects.shared.foundation.core)
            implementation(projects.shared.foundation.core.test)
            implementation(projects.shared.foundation.core.impl)
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