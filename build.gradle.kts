import de.stefan.lang.designsystem.DesignSystemGeneratorIOS
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)
    alias(libs.plugins.detekt)
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.dependencyAnalysis)
}

dependencies {
    detektPlugins(libs.detekt.formatting)
    detektPlugins(libs.detekt.compose.rules)
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "com.autonomousapps.dependency-analysis")

    detekt {
        buildUponDefaultConfig = true
        config.from(file("${rootProject.projectDir}/config/detekt/config.yml"))
        autoCorrect = true
        source.from(
            "src/androidMain/kotlin/",
            "src/androidTest/kotlin/",
            "src/commonMain/kotlin/",
            "src/commonTest/kotlin/",
            "src/main/java/",
            "buildSrc/src/main/kotlin"
        )
    }

    plugins.withType<org.jetbrains.kotlin.gradle.plugin.KotlinBasePluginWrapper> {
        dependencies {
            detektPlugins(libs.detekt.formatting)
            detektPlugins(libs.detekt.compose.rules)
        }
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        jvmTarget = Project.Android.BuildSettings.javaVersion.toString()
    }

    tasks.withType<KotlinCompilationTask<*>>().configureEach {
        compilerOptions {
            freeCompilerArgs.add("-opt-in=kotlin.time.ExperimentalTime")
        }
    }

    plugins.withId("org.jetbrains.kotlin.multiplatform") {
        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.findByName("commonTest")?.dependencies {
                implementation(libs.kotlin.test.annotations.common)
            }
        }
    }
}

tasks.register<Copy>("installGitHooks") {
    val gitHooksDir = file(".git/hooks")
    val githooksSourceDir = file("githooks")

    from(githooksSourceDir)
    into(gitHooksDir)

    doFirst {
        if (!gitHooksDir.exists()) {
            gitHooksDir.mkdirs()
        }
    }
}
