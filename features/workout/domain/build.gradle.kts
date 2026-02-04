import de.stefan.lang.di.configureDi

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.detekt)
}

configureDi(
    moduleClassName = "de.stefan.lang.shapebyte.features.workout.domain.WorkoutDomainModule",
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
            api(projects.features.workout.domain.contract)
            implementation(projects.features.workout.domain.implementation)
            implementation(projects.features.workout.data)

            implementation(projects.core.di)
            implementation(projects.core.logging)
            implementation(projects.core.coroutines)
            implementation(projects.features.featureToggles.data.contract)
            implementation(projects.features.featureToggles.domain.contract)

        }

        commonTest.dependencies {
            implementation(projects.core.test)
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.test.annotations.common)
        }
    }
}

android {
    namespace = "de.stefan.lang.shapebyte.features.workout.domain"
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
