plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.detekt)
}

kotlin {
    explicitApi()
    androidTarget()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.test)
            implementation(projects.core.di)

            implementation(libs.koin.test)
            implementation(projects.core.logging)
            implementation(projects.core.utils)
            implementation(projects.foundation.core)

            implementation(projects.features.workout.data)
            implementation(projects.features.workout.domain)
        }
    }
}

android {
    namespace = "de.stefan.lang.shapebyte.features.workout.presentation.test"
    compileSdk = Project.Android.BuildSettings.targetSdk
    defaultConfig {
        minSdk = Project.Android.BuildSettings.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
