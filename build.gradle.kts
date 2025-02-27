plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)
    alias(libs.plugins.detekt)
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
        source.from("src/androidMain/kotlin/", "src/androidTest/kotlin/", "src/commonMain/kotlin/", "src/commonTest/kotlin/", "src/main/java/")
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