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
            api(projects.core.coroutines.api)

            implementation(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(projects.core.coroutines.impl)
            implementation(projects.core.coroutines.test)
            implementation(projects.core.di)
        }
    }
}

android {
    namespace = "de.stefan.lang.coroutines"
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
