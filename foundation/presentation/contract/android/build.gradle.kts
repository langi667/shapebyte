plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.detekt)
}

android {
    namespace = "de.stefan.lang.foundation.presentation.contract.android"
    compileSdk = Project.Android.BuildSettings.targetSdk

    defaultConfig {
        minSdk = Project.Android.BuildSettings.minSdk
    }

    compileOptions {
        sourceCompatibility = Project.Android.BuildSettings.javaVersion
        targetCompatibility = Project.Android.BuildSettings.javaVersion
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += Project.Android.BuildSettings.excludedResourcesList
        }
    }
}

dependencies {
    api(projects.foundation.presentation.contract)

    implementation(libs.compose.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
}
