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
            api(projects.foundation.core.contract)
            api(projects.core.logging.contract)
            api(projects.core.coroutines.contract)

            implementation(libs.kotlinx.coroutines.core)
        }

        androidMain.dependencies {
            api(libs.androidx.lifecycle.viewmodel)
            api(libs.androidx.lifecycle.viewmodel.ktx)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(projects.core.test)
            implementation(projects.foundation.core.fake)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

android {
    namespace = "de.stefan.lang.foundation.presentation.contract"
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
