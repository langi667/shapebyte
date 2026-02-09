import org.gradle.kotlin.dsl.implementation
import org.gradle.kotlin.dsl.invoke

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.detekt)
    alias(libs.plugins.compose.compiler)
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
            api(projects.core.di)
            api(projects.core.logging.contract)
            api(projects.core.coroutines.contract)

            implementation(libs.kotlinx.coroutines.core)
        }

        androidMain.dependencies {
            api(libs.androidx.lifecycle.viewmodel)
            api(libs.androidx.lifecycle.viewmodel.ktx)
            implementation(libs.compose.ui)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(projects.core.test)

            implementation(projects.core.logging)
            implementation(projects.core.coroutines)

            implementation(projects.foundation.core.fake)
            implementation(libs.kotlinx.coroutines.test)
        }

        androidUnitTest.dependencies {
            implementation(libs.junit)
            implementation(libs.mockk.android)
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
