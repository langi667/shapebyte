import de.stefan.lang.di.configureDi

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.detekt)
}

configureDi(
    moduleClassName = "de.stefan.lang.shapebyte.features.workout.data.WorkoutDataModule",
    transitive = true,
)

kotlin {
    explicitApi()
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
            api(projects.features.workout.data.contract)
            implementation(projects.features.workout.data.fixture)
            implementation(projects.features.workout.data.implementation)

            implementation(projects.core.di)
            implementation(projects.core.logging)

            implementation(projects.foundation.core)
            implementation(libs.koin.core)
        }
    }
}

android {
    namespace = "de.stefan.lang.shapebyte.features.workout.data"
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
