import de.stefan.lang.di.configureDi

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.detekt)
}

configureDi(
    moduleClassName = "de.stefan.lang.shapebyte.features.home.presentation.HomePresentationModule",
    transitive = false,
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

    sourceSets  {
        commonMain.dependencies {
            api(projects.features.home.presentation.contract)
            implementation(projects.core.di)
            implementation(projects.core.logging)
            implementation(projects.core.coroutines)

            implementation(projects.features.home.presentation.implementation)
            implementation (projects.features.featureToggles.domain)
            implementation (projects.features.navigation)

        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.turbine)
            implementation(libs.kotlinx.coroutines.test)
            implementation (libs.koin.test)

            implementation (projects.core.test)
            implementation (projects.foundation.core.fake)
        }

        androidUnitTest.dependencies {
        }
    }
}

android {
    namespace = "de.stefan.lang.shapebyte.features.home.presentation"
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
