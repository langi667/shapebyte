plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.detekt)
}

kotlin {
    explicitApi()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget =
                    Project.Android.BuildSettings.javaVersion
                        .toString()
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.coroutines.contract)
            implementation(projects.core.di)
            implementation(libs.kotlinx.coroutines.test)
        }

        commonTest.dependencies {
            implementation(libs.koin.test)
            implementation(libs.turbine)
        }

        androidUnitTest.dependencies {
            implementation(libs.junit)
            implementation(libs.mockk.android)
        }
    }
}

android {
    namespace = "de.stefan.lang.coroutines.test"
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
