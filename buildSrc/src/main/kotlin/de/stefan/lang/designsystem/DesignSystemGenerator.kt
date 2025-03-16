package de.stefan.lang.designsystem
import java.io.File

class DesignSystemGenerator {
    fun generate(outputDir: File) {
        println("Generate Android Theme")

        DesignSystemGeneratorAndroid().generate(outputDir)

        println("DesignSystem Theme generated successfully! to ${outputDir.absolutePath}")
    }
}