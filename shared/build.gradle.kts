import de.stefan.lang.designsystem.DesignSystemGeneratorIOS
import de.stefan.lang.di.configureDi
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.detekt)
    id("co.touchlab.skie") version "0.10.1"
    kotlin("plugin.serialization") version "2.0.0"
}

configureDi(
    moduleClassName = "de.stefan.lang.shapebyte.SharedModule",
    transitive = true,
)

tasks.register("allTestDebugUnitTest") {
    dependsOn(subprojects.mapNotNull { it.tasks.findByName("testDebugUnitTest") })
}

kotlin {
    explicitApi()
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

        commonMain.dependencies {
            api(projects.shared.contract)
            api(projects.core.utils.contract)
            api(projects.core.logging.contract)

            api(projects.foundation.core.contract)
            api(projects.foundation.presentation.contract)
            api(projects.designsystem.contract)
            api(projects.features.featureToggles.data.contract)
            api(projects.features.featureToggles.domain.contract)

            api(projects.features.home.presentation.contract)
            api(projects.features.workout.data.contract)
            api(projects.features.workout.presentation.contract)
            api(projects.features.navigation.contract)

            implementation(projects.shared.implementation)
            implementation(projects.core.di)
            implementation(projects.core.coroutines)

            implementation(projects.core.utils)
            implementation(projects.core.logging)
            implementation(projects.foundation.core)
            implementation(projects.foundation.presentation)

            implementation(projects.features.featureToggles.data)
            implementation(projects.features.featureToggles.domain)

            implementation(projects.features.navigation)

            implementation(projects.features.workout.data)
            implementation(projects.features.workout.domain)
            implementation(projects.features.workout.presentation)
            implementation(projects.features.home.presentation)

            implementation(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)





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
            export(projects.core.utils.contract)
            export(projects.shared.contract)
            export(projects.foundation.core.contract)
            export(projects.foundation.presentation.contract)
            export(projects.designsystem.contract)
            export(projects.features.featureToggles.domain.contract)
            export(projects.features.home.presentation.contract)
            export(projects.features.workout.presentation.contract)
            export(projects.features.workout.data.contract)
            export(projects.features.navigation.contract)

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
