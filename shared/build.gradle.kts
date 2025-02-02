import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.detekt)
    id("co.touchlab.skie") version "0.8.4"
    kotlin("plugin.serialization") version "2.0.0"
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = App.Android.BuildSettings.javaVersion.toString()
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = false
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.lifecycle.viewmodel.ktx)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)

            api(projects.shared.core)
            api(projects.shared.foundation.base)
            api(projects.shared.foundation.ui)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.turbine)
            implementation(libs.kotlinx.coroutines.test)
            implementation (libs.koin.test)

            implementation(projects.shared.testcore)
//            implementation(projects.shared.core)
//            implementation(projects.shared.foundation.core)
//            implementation(projects.shared.foundation.ui)
        }
//        iosMain.dependencies {
//            implementation(projects.shared.core)
//            implementation(projects.shared.foundation.core)
//        }

    }

    targets.withType<KotlinNativeTarget>{
        binaries.withType<Framework> {
            isStatic = false
            export(projects.shared.core)
            export(projects.shared.foundation.base)
            export(projects.shared.foundation.ui)
//
//            @OptIn(ExperimentalKotlinGradlePluginApi::class)
//            transitiveExport = true
        }
    }
}

android {
    namespace = "de.stefan.lang.shapebyte"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = App.Android.BuildSettings.javaVersion
        targetCompatibility = App.Android.BuildSettings.javaVersion
    }

    packaging {
        resources {
            excludes += App.Android.BuildSettings.excludedResourcesList
        }
    }

    sourceSets {
        defaultConfig {
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation(libs.androidx.junit.ktx)

    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.kotlin.test)
    androidTestImplementation(libs.turbine)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation (libs.koin.test)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.junit.jupiter)
    androidTestImplementation(projects.shared.core.test)
}
