import de.stefan.lang.designsystem.DesignSystemGenerator

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.screenshot)
}

android {
    namespace = "de.stefan.lang.shapebyte.android"
    compileSdk = Project.Android.BuildSettings.targetSdk
    defaultConfig {
        applicationId = "de.stefan.lang.shapebyte.android"
        minSdk = Project.Android.BuildSettings.minSdk
        targetSdk = Project.Android.BuildSettings.targetSdk
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += Project.Android.BuildSettings.excludedResourcesList
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }

    }
    compileOptions {
        sourceCompatibility = Project.Android.BuildSettings.javaVersion
        targetCompatibility = Project.Android.BuildSettings.javaVersion
    }
    kotlinOptions {
        jvmTarget = Project.Android.BuildSettings.javaVersion.toString()
    }

    sourceSets {
        defaultConfig {
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        getByName("main") {
            kotlin.srcDir("generated/theme/de/stefan/lang/designsystem/theme/")
        }
    }
    flavorDimensions += "environment"

    productFlavors {
        create("development") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            isDefault = true
        }

        create("staging") {
            dimension = "environment"
            applicationIdSuffix = ".staging"
            versionNameSuffix = "-staging"
        }

        create("production") {
            dimension = "environment"
        }
    }

    experimentalProperties["android.experimental.enableScreenshotTest"] = true
}

dependencies {
    implementation(projects.shared)

    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.core.splashscreen)
    implementation(libs.androidx.junit.ktx)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.coil.common)
    implementation(libs.coil.compose)
    implementation(libs.androidx.navigation.compose)
    screenshotTestImplementation(libs.compose.ui.tooling)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.turbine)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation (libs.koin.test)
    testImplementation(libs.junit)
    testImplementation(projects.shared.core.coreTest)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}

tasks.register("generateDesignSystem") {
    outputs.dir("$rootDir/androidApp/generated/theme")
    val outputDir = file(outputs.files.single().absolutePath)


    doLast {
        outputDir.mkdirs()
        val designSystemGenerator = DesignSystemGenerator()
        designSystemGenerator.generate(outputDir)
    }
}

tasks.named("preBuild") {
    dependsOn("generateDesignSystem")
}