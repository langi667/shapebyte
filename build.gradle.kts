import de.stefan.lang.designsystem.DesignSystemGeneratorIOS

plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)
    alias(libs.plugins.detekt)
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
}

dependencies {
    detektPlugins(libs.detekt.formatting)
    detektPlugins(libs.detekt.compose.rules)
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
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