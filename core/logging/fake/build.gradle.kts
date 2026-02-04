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
            api(projects.core.logging.contract)
            implementation(projects.core.logging.implementation)

            implementation(libs.koin.core)
        }
    }
}

android {
    namespace = "de.stefan.lang.utils.logging.fake"
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
