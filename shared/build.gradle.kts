import de.stefan.lang.designsystem.DesignSystemGeneratorIOS
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.detekt)
    id("co.touchlab.skie") version "0.10.1"
    kotlin("plugin.serialization") version "2.0.0"
}

tasks.register("allTestDebugUnitTest") {
    dependsOn(subprojects.mapNotNull { it.tasks.findByName("testDebugUnitTest") })
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = Project.Android.BuildSettings.javaVersion.toString()
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
            implementation(libs.androidx.junit.ktx)
        }

        androidInstrumentedTest.dependencies {
            implementation(libs.mockk.android)
            implementation(libs.kotlin.test)
            implementation(libs.turbine)
            implementation(libs.kotlinx.coroutines.test)
            implementation (libs.koin.test)
            implementation (libs.junit.jupiter)
            implementation(projects.core.test)

            implementation(libs.androidx.test.ext.junit)
            implementation(libs.androidx.test.espresso.core)
        }

        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)

            api(projects.core)
            api(projects.shared.foundation)
            api(projects.shared.designsystem)
            api(projects.shared.features)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.turbine)
            implementation(libs.kotlinx.coroutines.test)
            implementation (libs.koin.test)
            implementation (projects.core.test)
        }
    }

    targets.withType<KotlinNativeTarget>{
        binaries.withType<Framework> {
            isStatic = false
            export(projects.core)
            export(projects.shared.foundation)
            export(projects.shared.designsystem)
            export(projects.shared.features)

            transitiveExport = true
        }
    }

    targets.configureEach {
        compilations.configureEach {
            compileTaskProvider.get().compilerOptions {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
    }
}

android {
    namespace = "de.stefan.lang.shapebyte"
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

tasks.register("generateDesignSystemIOS") {
    outputs.dir("$rootDir/iosApp/iosApp/generated/theme")
    val iOSThemeFilePath = file(outputs.files.single().absolutePath)

    doLast {
        iOSThemeFilePath.mkdirs()
        val designSystemGenerator = DesignSystemGeneratorIOS()
        designSystemGenerator.generate(iOSThemeFilePath)
    }
}

// Configure the dependency on the iOS compilation tasks
tasks.matching { it.name.contains("embedAndSignAppleFramework") }.configureEach {
    dependsOn("generateDesignSystemIOS")
}