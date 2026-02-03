
import de.stefan.lang.designsystem.DesignSystemGeneratorAndroid
import de.stefan.lang.designsystem.DesignSystemGeneratorIOS
import java.util.Locale

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

    applicationVariants.all {
        val variant = this

        val variantNameCapitalized = variant.name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }

        val compileKotlinTaskName = "compile${variantNameCapitalized}Kotlin"

        tasks.named(compileKotlinTaskName) {
            dependsOn(tasks.named("generateDesignSystemAndroid"))
        }

        val explodeCodeSourceTaskName = "explodeCodeSource${variantNameCapitalized}"
        
        tasks.configureEach {
            if (name == explodeCodeSourceTaskName) {
                dependsOn(tasks.named("generateDesignSystemAndroid"))
            }
        }
    }
}

dependencies {
    implementation(projects.shared)
    implementation(projects.core.di)

    implementation(projects.features.workout.data.contract)

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
    implementation(libs.coil.compose)
    implementation(libs.androidx.navigation.compose)
    screenshotTestImplementation(libs.compose.ui.tooling)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}



tasks.register("generateDesignSystemAndroid") {
    group = "generation"
    description = "Generates Android design system theme files."
    val generatedThemeDir = project.layout.buildDirectory.dir("generated/custom_theme_sources")
    outputs.dir(generatedThemeDir)

    doLast {
        val outputDirFile = generatedThemeDir.get().asFile
        outputDirFile.mkdirs()

        val designSystemGenerator = DesignSystemGeneratorAndroid()
        designSystemGenerator.generate(outputDirFile)
    }
}

android {
    // ...
    sourceSets {
        getByName("main") {
            kotlin.srcDir(project.layout.buildDirectory.dir("generated/custom_theme_sources"))
        }
    }
    // ...
}
