import de.stefan.lang.di.configureDi

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.detekt)
    kotlin("plugin.serialization") version "2.0.0"
}

configureDi(
    moduleClassName = "de.stefan.lang.foundationCore.FoundationCoreModule",
    transitive = true,
)

kotlin {
    explicitApi()
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        androidMain.dependencies {
            
        }

        commonMain.dependencies {
            api(projects.foundation.core.contract)

            implementation(libs.koin.core)
            implementation(projects.foundation.core.implementation)
            implementation(projects.foundation.core.fake)
            implementation(projects.core.di)
            implementation(projects.core.coroutines)
            implementation(projects.core.utils)
            implementation(projects.core.logging)
            implementation (libs.kotlinx.datetime)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.test.annotations.common)

            implementation (libs.koin.test)


            implementation (projects.core.test)
        }
    }
}

android {
    namespace = "de.stefan.lang.foundation.core"
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
