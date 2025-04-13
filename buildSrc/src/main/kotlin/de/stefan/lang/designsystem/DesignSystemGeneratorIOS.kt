package de.stefan.lang.designsystem

import de.stefan.lang.designsystem.color.ios.AssetColorSetCreator
import de.stefan.lang.designsystem.font.TextStylesCollection
import de.stefan.lang.designsystem.platformspecific.Platform

import io.outfoxx.swiftpoet.CodeBlock
import io.outfoxx.swiftpoet.DeclaredTypeName
import io.outfoxx.swiftpoet.ExtensionSpec
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
        val colors = generateColors(outputFile)
        val themeClass = themeClass(colors)

        val dimensionExtension = dimensionsExtension()
        val fontsExtension = fontsExtension()
        val colorsExtension = colorsExtension(colors)
        val roundedCornerShapesExtension = roundedCornerShapesExtension()
        val animationDurationExtension = animationDurationExtension()

        val file = FileSpec.builder("Theme")
            .addType(themeClass)
            .addExtension(dimensionExtension)
            .addExtension(spacingsExtension("Double"))
            .addExtension(spacingsExtension("CGFloat"))
            .addExtension(fontsExtension)
            .addExtension(colorsExtension)
            .addExtension(roundedCornerShapesExtension)
            .addExtension(animationDurationExtension)

            .addImport("SwiftUI")
            .addImport("shared")
            .build()

        file.writeTo(outputFile)
    }

    private fun dimensionsExtension(): ExtensionSpec {
        val dimensionsExtension = ExtensionSpec.builder(
            extendedType = DeclaredTypeName.qualifiedTypeName(".Double")
        )

        themeDataProvider.dimensions.allSorted.forEach { currEntry ->
            val propertyName = "dimension" + currEntry.key.replaceFirstChar { it.uppercaseChar() }
            val initializer = CodeBlock.builder().add( "Theme.dimensions.${currEntry.key}" ).build()
            val property = PropertySpec
                .builder(
                    name = propertyName,
                    type = TypeVariableName(
                        name = "Double")
                )
                .addModifiers(Modifier.STATIC)
                .initializer( initializer )

            dimensionsExtension.addProperty( property.build() )
        }

        dimensionsExtension.addModifiers(Modifier.PUBLIC)
        return  dimensionsExtension.build()
    }

    private fun colorsExtension(generatedColors: List<String>): ExtensionSpec {
        val colorExtension = ExtensionSpec.builder(
            extendedType = DeclaredTypeName.qualifiedTypeName("SwiftUI.Color")
        )

        val colorProperties = colorPropertiesSorted(generatedColors)
        colorProperties.forEach { currColor ->
            val propertyName = "SB" + currColor.replaceFirstChar { it.uppercaseChar() }
            val initializer = CodeBlock.builder().add( "Theme.colors.$currColor")

            val property = PropertySpec
                .builder(
                    name = propertyName,
                    type = TypeVariableName(
                        name = "SwiftUI.Color")
                )
                .addModifiers(Modifier.STATIC)
                .initializer( initializer.build() )

            colorExtension.addProperty(property.build())
        }

        return colorExtension.build()
    }

    private fun roundedCornerShapesExtension(): ExtensionSpec {
        val roundedCornerShapeExtension = ExtensionSpec.builder(
            extendedType = DeclaredTypeName.qualifiedTypeName(".Double")
        )

        themeDataProvider.shapes.roundedCorners.allSorted.forEach { currRoundedCorner ->
            val propertyName = "roundedCornerShape${currRoundedCorner.key.replaceFirstChar { it.uppercaseChar() }}"
            val initializer = CodeBlock.builder().add( "Theme.roundedCornerShapes.${currRoundedCorner.key}")

            val property = PropertySpec
                .builder(
                    name = propertyName,
                    type = TypeVariableName(
                        name = "Double")
                )
                .addModifiers(Modifier.STATIC)
                .initializer( initializer.build() )
            roundedCornerShapeExtension.addProperty(property.build())
        }

        roundedCornerShapeExtension.addModifiers(Modifier.PUBLIC)
        return  roundedCornerShapeExtension.build()
    }


    private fun fontsExtension(): ExtensionSpec {
        val fontsExtension = ExtensionSpec.builder(
            extendedType = DeclaredTypeName.qualifiedTypeName("SwiftUI.Font")
        )

        val textStyles: TextStylesCollection = themeDataProvider.textStyles as TextStylesCollection
        val allTextStyles = textStyles.all

        allTextStyles.entries.forEach { currTextStyle ->
            val propertyName = "SB" + currTextStyle.key.replaceFirstChar { it.uppercaseChar() }
            val initializer = CodeBlock.builder().add( "Theme.fonts.${currTextStyle.key}")

            val property = PropertySpec
                .builder(
                    name = propertyName,
                    type = TypeVariableName(
                        name = "SwiftUI.Font")
                )
                .addModifiers(Modifier.STATIC)
                .initializer( initializer.build() )

            fontsExtension.addProperty(property.build())
        }

        fontsExtension.addModifiers(Modifier.PUBLIC)
        return  fontsExtension.build()
    }

    private fun spacingsExtension(baseType: String = "Double"): ExtensionSpec {
        val spacingsExtension = ExtensionSpec.builder(
            extendedType = DeclaredTypeName.qualifiedTypeName(".$baseType")
        )

        themeDataProvider.spacings.allSorted.forEach { currEntry ->
            val propertyName = "spacing" + currEntry.key.replaceFirstChar { it.uppercaseChar() }
            val initializer = CodeBlock.builder().add( "Theme.spacings.${currEntry.key}" ).build()
            val property = PropertySpec
                .builder(
                    name = propertyName,
                    type = TypeVariableName(
                        name = baseType)
                )
                .addModifiers(Modifier.STATIC)
                .initializer( initializer )

            spacingsExtension.addProperty( property.build() )
        }

        spacingsExtension.addModifiers(Modifier.PUBLIC)
        return  spacingsExtension.build()
    }

    private fun themeClass(generatedColors: List<String>): TypeSpec {
        val themeClass = TypeSpec
            .structBuilder("Theme")
            .addModifiers(Modifier.PUBLIC)
            .addProperty(dimensions())
            .addProperty(spacings())
            .addProperty(fonts())
            .addProperty(colors(generatedColors))
            .addProperty(roundedCornerShapes())
            .addProperty(animationDurations())

        return themeClass.build()
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

    private fun animationDurationExtension(): ExtensionSpec {
        val animationDurationExtension = ExtensionSpec.builder(
            extendedType = DeclaredTypeName.qualifiedTypeName(".Double")
        )

        themeDataProvider.animationDurations.allSorted.forEach { currEntry ->
            val propertyName = "animationDuration" + currEntry.key.replaceFirstChar { it.uppercaseChar() }
            val initializer = CodeBlock.builder().add( "Theme.animationDurations.${currEntry.key}" ).build()
            val property = PropertySpec
                .builder(
                    name = propertyName,
                    type = TypeVariableName(
                        name = "Double")
                )
                .addModifiers(Modifier.STATIC)
                .initializer( initializer )

            animationDurationExtension.addProperty( property.build() )
        }

        animationDurationExtension.addModifiers(Modifier.PUBLIC)
        return  animationDurationExtension.build()
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

        for ((index, currDimension) in themeDataProvider.dimensions.allSorted.entries.withIndex()) {
            retVal.add("${currDimension.key}: ${currDimension.value}")
            if (index < themeDataProvider.dimensions.allSorted.size - 1) {
                retVal.add(",")
            }

            retVal.add("\n")
        }

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

        for ((index, currSpacing) in themeDataProvider.spacings.allSorted.entries.withIndex()) {
            retVal.add("${currSpacing.key}: ${currSpacing.value}")
            if (index < themeDataProvider.spacings.allSorted.size - 1) {
                retVal.add(",")
            }

            retVal.add("\n")
        }

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

    private fun colors(colorNames: List<String>): PropertySpec {
        val typeName = "Colors"
        val colorsProperty = PropertySpec.builder(
            "colors",
            TypeVariableName(typeName)
        ).addModifiers(Modifier.PUBLIC, Modifier.STATIC)

        val colorsInitializer = CodeBlock
            .builder()
            .add("$typeName(")
            .add(colorsInitializerArgs(colorNames))
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

    private fun colorsInitializerArgs(colors: List<String>): CodeBlock {
        println("Color: $colors")
        val codeBlock = CodeBlock.builder()
        val colorProperties = colorPropertiesSorted(colors)

        for ((index, colorName) in colorProperties.withIndex()) {
            val colorProperty = colorName.replaceFirstChar { it.lowercase() }.replace("Color", "")
            val assetName = colorName.replaceFirstChar { it.uppercaseChar() } + "Color"
            var colorCodeStr = "\n$colorProperty: Color(\"$assetName\")"

            if (index < colors.size - 1) {
                colorCodeStr += ","
            }

            codeBlock.add(colorCodeStr)
        }

        return codeBlock.build()
    }

    private fun colorPropertiesSorted(generatedColors: List<String>) = generatedColors
            .map { it.replaceFirstChar { it.lowercase() }.replace("Color", "") }
            .sorted()
}