import de.stefan.lang.di.configureDi

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.detekt)
    kotlin("plugin.serialization") version "2.0.0"
}

configureDi(
    moduleClassName = "de.stefan.lang.designsystem.DesignSystemModule",
    transitive = true,
)

kotlin {
    explicitApi()
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(projects.designsystem.contract)
            implementation(projects.core.di)
        }

        commonTest.dependencies {
            implementation(projects.core.test)
        }
    }
}

android {
    namespace = "de.stefan.lang.designsystem"
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

tasks.register("allTestDebugUnitTest") {
    dependsOn(subprojects.mapNotNull { it.tasks.findByName("testDebugUnitTest") })
}
