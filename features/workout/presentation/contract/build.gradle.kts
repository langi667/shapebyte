plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.detekt)
}

kotlin {
    explicitApi()

    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.foundation.presentation.contract)
            implementation(projects.core.logging.contract)
            implementation(projects.core.coroutines.contract)
            implementation(projects.core.di)

            implementation(projects.core.utils)
            implementation(projects.features.navigation.contract)

            implementation(projects.designsystem)
            implementation(projects.foundation.core.contract)
            implementation(projects.features.workout.data)
            implementation(projects.features.workout.data.contract)
            implementation(projects.features.workout.data.fixture)

            implementation(libs.kotlinx.coroutines.core)
            
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "de.stefan.lang.shapebyte.features.workout.presentation.contract"
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
