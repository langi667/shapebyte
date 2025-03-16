plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup:kotlinpoet:1.15.3") // Or the latest version
    implementation("com.squareup:kotlinpoet-ksp:1.15.3") // Or the latest version
    implementation(kotlin("script-runtime"))
}