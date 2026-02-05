
import com.android.build.api.dsl.ApplicationExtension
import de.stefan.lang.designsystem.DesignSystemGeneratorAndroid
import de.stefan.lang.designsystem.DesignSystemGeneratorIOS
import org.gradle.language.nativeplatform.internal.Dimensions.applicationVariants
import java.util.Locale
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.screenshot)
}

configure<ApplicationExtension> {
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

    sourceSets {
        defaultConfig {
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        getByName("main") {
            kotlin.srcDir("build/generated/custom_theme_sources")
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

kotlin {
    compilerOptions {
        jvmTarget.set(
            JvmTarget.fromTarget(Project.Android.BuildSettings.javaVersion.majorVersion)
        )
    }
}

abstract class GenerateDesignSystemAndroidTask : DefaultTask() {
    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @TaskAction
    fun run() {
        val dir = outputDir.get().asFile
        dir.mkdirs()
        DesignSystemGeneratorAndroid().generate(dir)
    }
}

val generateDesignSystemAndroid =
    tasks.register<GenerateDesignSystemAndroidTask>("generateDesignSystemAndroid") {
        outputDir.set(layout.buildDirectory.dir("generated/custom_theme_sources"))
    }

androidComponents {
    onVariants { variant ->
        variant.sources.kotlin?.addGeneratedSourceDirectory(
            generateDesignSystemAndroid,
            GenerateDesignSystemAndroidTask::outputDir
        )
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
    implementation(libs.compose.icons.core)
    implementation(libs.compose.icons.ext)

    screenshotTestImplementation(libs.compose.ui.tooling)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
