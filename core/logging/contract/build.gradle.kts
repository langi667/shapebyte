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
            // No external dependencies for contracts
        }
    }
}

android {
    namespace = "de.stefan.lang.utils.logging.contract"
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
