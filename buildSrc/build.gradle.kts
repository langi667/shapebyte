plugins {
    `kotlin-dsl`
    `version-catalog`
    kotlin("plugin.serialization") version "2.0.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("script-runtime"))
    implementation(libs.kotlinpoet)
    implementation(libs.kotlinpoet.ksp)
    implementation (libs.swiftpoet)
    implementation (libs.kotlinx.serialization.json)
}