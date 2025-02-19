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
                jvmTarget = App.Android.BuildSettings.javaVersion.toString()
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
            implementation(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)

            implementation(projects.shared.core)
            implementation(projects.shared.foundation.foundationCore)
        }

        commonTest.dependencies {
            implementation(projects.shared.core.coreTest)
            implementation(projects.shared.core.coreCoroutines.coreCoroutinesProvidingTest)
        }
    }
}

android {
    namespace = "de.stefan.lang.shapebyte.featureCore"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = App.Android.BuildSettings.javaVersion
        targetCompatibility = App.Android.BuildSettings.javaVersion
    }

    packaging {
        resources {
            excludes += App.Android.BuildSettings.excludedResourcesList
        }
    }

    sourceSets {
        defaultConfig {
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation(libs.androidx.junit.ktx)
    testImplementation(project(":shared:core:coreCoroutines"))
}