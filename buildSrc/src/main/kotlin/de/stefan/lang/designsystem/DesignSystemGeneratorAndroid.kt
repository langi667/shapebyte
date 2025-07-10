package de.stefan.lang.designsystem

import com.squareup.kotlinpoet.*
import de.stefan.lang.designsystem.platformspecific.Platform
import java.io.File

class DesignSystemGeneratorAndroid: DesignSystemGenerating {
    private val themeDataProvider: ThemeDataProvider = ThemeDataProvider(
        Platform.Android,
        ThemeData()
    )

    override fun generate(outputFile: File) {
        DesignSystemThemeGeneratorAndroid().generate(outputFile)
        generateThemeAdditions(outputFile)
        println("DesignSystem Android Theme generated successfully! to ${outputFile.absolutePath}")
    }

    private fun generateThemeAdditions(outputFile: File) {
        val objectName = "ThemeAdditions"
        val packageName = "de.stefan.lang.designsystem.theme"

        val themeAdditions = TypeSpec.objectBuilder(objectName)
            .addProperty(loggerProperty())
            .addProperty(animationDurationsProperty())
            .addProperty(dimensionsProperty())
            .addProperty(spacingsProperty())
            .build()

        val fileSpec = FileSpec.builder(packageName, objectName)
            .addType(themeAdditions)
            //.addImport("de.stefan.lang.coreutils.api", "Logging")
            .addImport("de.stefan.lang.designsystem", "Dimensions")
            .addImport("de.stefan.lang.designsystem", "Spacings")
            .addImport("de.stefan.lang.shapebyte", "SharedModule")
            .build()

        fileSpec.writeTo(outputFile)
    }

    private fun loggerProperty(): PropertySpec {
        return PropertySpec.builder(
            "logger",
            ClassName("de.stefan.lang.coreutils.api.logging", "Logging")
        )
            .initializer("SharedModule.logger()")
            .build()

    }

    private fun animationDurationsProperty(): PropertySpec {
        var initializer = "AnimationDuration("
        themeDataProvider.animationDurations.allSorted.forEach {
            initializer +=
                "\n${it.key} = ${it.value.toInt()},"
        }

        initializer += "\n)"

        val property = PropertySpec.builder(
            name = "animationDurations",
            type = ClassName(
                packageName = "de.stefan.lang.shapebyte.android.designsystem.ui", "AnimationDuration")
        )
            .initializer(initializer)
            .build()

        return property
    }

    private fun dimensionsProperty(): PropertySpec {
        var initializer = "Dimensions("
        themeDataProvider.dimensions.allSorted.forEach {
            initializer +=
                "\n${it.key} = ${it.value},"
        }

        initializer += "\n)"

        val property = PropertySpec.builder(
            name = "dimensions",
            type = ClassName(
                packageName = "de.stefan.lang.designsystem", "Dimensions")
        )
            .initializer(initializer)
            .build()

        return property
    }

    private fun spacingsProperty(): PropertySpec {
        var initializer = "Spacings("
        themeDataProvider.spacings.allSorted.forEach {
            initializer +=
                "\n${it.key} = ${it.value},"
        }

        initializer += "\n)"

        val property = PropertySpec.builder(
            name = "spacings",
            type = ClassName(
                packageName = "de.stefan.lang.designsystem", "Spacings")
        )
            .initializer(initializer)
            .build()

        return property
    }
}