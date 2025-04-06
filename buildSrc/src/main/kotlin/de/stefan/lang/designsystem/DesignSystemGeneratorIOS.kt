package de.stefan.lang.designsystem

import de.stefan.lang.designsystem.color.ios.AssetColorSetCreator
import de.stefan.lang.designsystem.font.TextStylesCollection
import de.stefan.lang.designsystem.platformspecific.Platform

import io.outfoxx.swiftpoet.CodeBlock
import io.outfoxx.swiftpoet.FileSpec
import io.outfoxx.swiftpoet.Modifier
import io.outfoxx.swiftpoet.PropertySpec
import io.outfoxx.swiftpoet.TypeSpec
import io.outfoxx.swiftpoet.TypeVariableName

import java.io.File

class DesignSystemGeneratorIOS : DesignSystemGenerating {
    private val themeDataProvider: ThemeDataProvider = ThemeDataProvider(
        Platform.iOS,
        ThemeData()
    )

    override fun generate(outputFile: File) {
        val themeClass = TypeSpec
            .structBuilder("Theme")
            .addModifiers(Modifier.PUBLIC)
            .addProperty(dimensions())
            .addProperty(spacings())
            .addProperty(fonts())
            .addProperty(colors(outputFile))
            .addProperty(roundedCornerShapes())
            .addProperty(animationDurations())

        val file = FileSpec.builder("Theme")
            .addType(themeClass.build())
            .addImport("SwiftUI")
            .addImport("shared")
            .build()

        file.writeTo(outputFile)
    }

    private fun animationDurations(): PropertySpec {
        val typeName = "AnimationDurations"
        val animationDurationsProperty = PropertySpec.builder(
            "animationDurations",
            TypeVariableName(name = typeName)
        ).addModifiers(Modifier.PUBLIC, Modifier.STATIC)

        val animationDurationsInitializer = CodeBlock
            .builder()
            .add("$typeName(")
            .add(animationDurationsInitializerArgs())
            .add("\n")
            .add(")")
            .add("\n")
            .build()

        animationDurationsProperty.initializer(animationDurationsInitializer)

        return animationDurationsProperty
            .build()
    }

    private fun animationDurationsInitializerArgs(): CodeBlock {
        val retVal = CodeBlock.builder()
        val animationDurations = themeDataProvider.animationDurations.allSorted
        for((index, animationDuration) in animationDurations.entries.withIndex()) {
            retVal.add("\n")
            retVal.add("${animationDuration.key}: ${animationDuration.value}")

            if (index < animationDurations.size - 1) {
                retVal.add(",")
            }
        }

        return retVal.build()
    }

    private fun roundedCornerShapes(): PropertySpec {
        val typeName = "RoundedCornerShapes"
        val roundedCornerShapesProperty = PropertySpec.builder(
            "roundedCornerShapes",
            TypeVariableName(name = typeName)
        ).addModifiers(Modifier.PUBLIC, Modifier.STATIC)

        val roundedCornerShapesInitializer = CodeBlock
            .builder()
            .add("$typeName(")
            .add(roundedCornerShapesInitializerArgs())
            .add("\n")
            .add(")")
            .add("\n")
            .build()

        roundedCornerShapesProperty.initializer(roundedCornerShapesInitializer)

        return roundedCornerShapesProperty
            .build()
    }

    private fun roundedCornerShapesInitializerArgs(): CodeBlock {
        val retVal = CodeBlock.builder()
        val roundedCorners = themeDataProvider.shapes.roundedCorners.allSorted

        for((index, roundedCornerShape) in roundedCorners.entries.withIndex()) {
            retVal.add("\n")
            retVal.add("${roundedCornerShape.key}: ${roundedCornerShape.value.radius}")

            if (index < roundedCorners.size - 1) {
                retVal.add(",")
            }
        }

        return retVal.build()
    }

    private fun dimensions(): PropertySpec {
        val typeName = "Dimensions"
        val dimensionsProperty = PropertySpec.builder(
            "dimensions",
            TypeVariableName(name = typeName)
        )
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)

        val dimensionInitializer = CodeBlock
            .builder()
            .add("$typeName(")
            .add(dimensionsInitializerArgs())
            .add("\n")
            .add(")")
            .add("\n")
            .build()

        dimensionsProperty.initializer(dimensionInitializer)

        return dimensionsProperty
            .build()
    }

    private fun dimensionsInitializerArgs(): CodeBlock {
        val retVal = CodeBlock.builder()
        retVal.add("\n")
        retVal.add("xTiny: ${themeDataProvider.dimensions.xTiny},")
        retVal.add("\n")

        retVal.add("tiny: ${themeDataProvider.dimensions.tiny},")
        retVal.add("\n")

        retVal.add("small: ${themeDataProvider.dimensions.small},")
        retVal.add("\n")

        retVal.add("medium: ${themeDataProvider.dimensions.medium},")
        retVal.add("\n")

        retVal.add("large: ${themeDataProvider.dimensions.large},")
        retVal.add("\n")

        retVal.add("xLarge: ${themeDataProvider.dimensions.xLarge},")
        retVal.add("\n")

        retVal.add("xxLarge: ${themeDataProvider.dimensions.xxLarge},")
        retVal.add("\n")

        retVal.add("xxxLarge: ${themeDataProvider.dimensions.xxxLarge}")

        return retVal.build()
    }

    private fun spacings(): PropertySpec {
        val typeName = "Spacings"
        val spacingsProperty = PropertySpec.builder(
            "spacings",
            TypeVariableName(typeName)
        ).addModifiers(Modifier.PUBLIC, Modifier.STATIC)

        val spacingInitializer = CodeBlock
            .builder()
            .add("$typeName(")
            .add(spacingsInitializerArgs())
            .add(")")
            .add("\n")
            .build()

        spacingsProperty.initializer(spacingInitializer)

        return spacingsProperty
            .build()
    }

    private fun spacingsInitializerArgs(): CodeBlock {
        val retVal = CodeBlock.builder()
        retVal.add("\n")
        retVal.add("xTiny: ${themeDataProvider.spacings.xTiny},")
        retVal.add("\n")

        retVal.add("tiny: ${themeDataProvider.spacings.tiny},")
        retVal.add("\n")

        retVal.add("small: ${themeDataProvider.spacings.small},")
        retVal.add("\n")

        retVal.add("medium: ${themeDataProvider.spacings.medium},")
        retVal.add("\n")

        retVal.add("large: ${themeDataProvider.spacings.large},")
        retVal.add("\n")

        retVal.add("xLarge: ${themeDataProvider.spacings.xLarge},")
        retVal.add("\n")

        retVal.add("xxLarge: ${themeDataProvider.spacings.xxLarge},")
        retVal.add("\n")

        retVal.add("xxxLarge: ${themeDataProvider.spacings.xxxLarge}")
        retVal.add("\n")

        return retVal.build()
    }

    private fun fonts(): PropertySpec {
        val typeName = "Fonts"
        val fontsProperty = PropertySpec.builder(
            "fonts",
            TypeVariableName(typeName)
        ).addModifiers(Modifier.PUBLIC, Modifier.STATIC)

        val fontsInitializer = CodeBlock
            .builder()
            .add("$typeName(")
            .add(fontsInitializerArgs())
            .add("\n")
            .add(")")
            .add("\n")
            .build()

        fontsProperty.initializer(fontsInitializer)
        return fontsProperty.build()
    }

    private fun fontsInitializerArgs(): CodeBlock {
        val codeBlock = CodeBlock.builder()
        val textStyles: TextStylesCollection = themeDataProvider.textStyles as TextStylesCollection
        val allTextStyles = textStyles.all

        for ((index, textStyle) in allTextStyles.entries.withIndex()) {
            var codeBlockStr =
                "\n${textStyle.key}: Font.system(size: ${textStyle.value.fontSize}, weight: .${textStyle.value.fontWeight.iOSName})"

            if (index < allTextStyles.size - 1) {
                codeBlockStr += ","
            }

            codeBlock.add(codeBlockStr)
        }

        return codeBlock.build()
    }

    private fun colors(outDir: File): PropertySpec {
        val createdColors = generateColors(outDir)
        return createColorProperty(createdColors)
    }

    private fun createColorProperty(colorNames: List<String>): PropertySpec {
        val typeName = "Colors"
        val colorsProperty = PropertySpec.builder(
            "colors",
            TypeVariableName(typeName)
        ).addModifiers(Modifier.PUBLIC, Modifier.STATIC)

        val colorsInitializer = CodeBlock
            .builder()
            .add("$typeName(")
            .add(colorsInitializerArgs())
            .add("\n")
            .add(")")
            .add("\n")
            .build()

        colorsProperty.initializer(colorsInitializer)
        return colorsProperty.build()
    }

    private fun generateColors(outDir: File): List<String> {
        val colors = mutableListOf<String>()
        val assetFileName = "ThemeColors.xcassets"
        val assetFilePath = "${outDir.absolutePath}/$assetFileName"

        File(assetFilePath).mkdirs()

        themeDataProvider.lightColorScheme.all.forEach { currColorEntry ->
            val color = currColorEntry.value
            val iOSName = currColorEntry
                .key
                .replaceFirstChar { it.uppercaseChar() }
                .plus("Color")

            val darkModeColor = themeDataProvider.darkColorScheme.get(currColorEntry.key)
            AssetColorSetCreator()
                .setColor(color)
                .setDarkColor(darkModeColor)
                .create(assetFilePath, iOSName)

            colors.add(iOSName)

        }

        return colors
    }

    private fun colorsInitializerArgs(): CodeBlock {
        val codeBlock = CodeBlock.builder()

        val colorsOrderedByConstructorArgs = listOf(
            "primary",
            "secondary",
            "background",
            "inversePrimary"
        )

        for ((index, colorName) in colorsOrderedByConstructorArgs.withIndex()) {
            val assetName = colorName.replaceFirstChar { it.uppercaseChar() } + "Color"
            var colorCodeStr = "\n$colorName: Color(\"$assetName\")"

            if (index < colorsOrderedByConstructorArgs.size - 1) {
                colorCodeStr += ","
            }

            codeBlock.add(colorCodeStr)
        }

        return codeBlock.build()
    }
}