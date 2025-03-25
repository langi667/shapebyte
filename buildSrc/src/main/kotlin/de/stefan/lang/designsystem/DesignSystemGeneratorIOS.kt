package de.stefan.lang.designsystem

import de.stefan.lang.designsystem.font.TextStylesCollection
import de.stefan.lang.designsystem.color.ios.AssetColorSetCreator

import io.outfoxx.swiftpoet.CodeBlock
import io.outfoxx.swiftpoet.FileSpec
import io.outfoxx.swiftpoet.Modifier
import io.outfoxx.swiftpoet.PropertySpec
import io.outfoxx.swiftpoet.TypeSpec
import io.outfoxx.swiftpoet.TypeVariableName

import java.io.File

class DesignSystemGeneratorIOS: DesignSystemGenerating {
    private val themeData: ThemeData = ThemeData

    override fun generate(outputFile: File) {
        val themeClass = TypeSpec
            .structBuilder("Theme")
            .addModifiers(Modifier.PUBLIC)
            .addProperty(dimensions())
            .addProperty(spacings())
            .addType(textStyles())
            .addType(colors(outputFile))
            .addType(shapes())

        animationDurations()?.let {
            themeClass.addType(it)
        }

        val file = FileSpec.builder("Theme")
            .addType(themeClass.build())
            .addImport("SwiftUI")
            .addImport("shared")
            .build()

        file.writeTo(outputFile)
    }

    private fun animationDurations(): TypeSpec? {
        val animationData = animationDurationFrom(themeData.iOS, themeData) ?: return null

        val animationDurationClass = TypeSpec
            .structBuilder("AnimationDuration")
            .addModifiers(Modifier.PUBLIC)

        animationData.allSorted.forEach {
            val animationDurationProperty = PropertySpec.builder(
                it.key,
                TypeVariableName("TimeInterval")
            )

            val animationDurationPropertyInitializer = CodeBlock.builder().add("${it.value}")
            animationDurationProperty.initializer(animationDurationPropertyInitializer.build())
            animationDurationProperty.addModifiers(Modifier.PUBLIC, Modifier.STATIC)

            animationDurationClass.addProperty(animationDurationProperty.build())
        }


        return animationDurationClass.build()
    }

    private fun shapes(): TypeSpec {
        val shapesClass = TypeSpec
            .structBuilder("Shapes")
            .addModifiers(Modifier.PUBLIC)

        val roundedCornersClass = TypeSpec
            .structBuilder("RoundedCorners")
            .addModifiers(Modifier.PUBLIC)


        themeData.shapes.roundedCorners.allSorted.forEach {
            val roundedCornerProperty = PropertySpec.builder(
                it.key,
                TypeVariableName("Double")
            )

            val roundedCornerPropertyInitializer = CodeBlock
                .builder()
                .add("${it.value.radius}")
                .build()

            roundedCornerProperty.initializer(roundedCornerPropertyInitializer)
            roundedCornerProperty.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            roundedCornersClass.addProperty(roundedCornerProperty.build())
        }

        shapesClass.addType(roundedCornersClass.build())
        return shapesClass.build()
    }

    private fun dimensions(): PropertySpec {
        val dimensionsProperty = PropertySpec.builder(
            "dimensions",
            TypeVariableName("Dimension")
        )
        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)

        val dimensionInitializer = CodeBlock
            .builder()
            .add("Dimension(")
            .add(dimensionProperties())
            .add("\n")
            .add(")")
            .add("\n")
            .build()

        dimensionsProperty.initializer(dimensionInitializer)

        return dimensionsProperty
            .build()
    }

    private fun dimensionProperties(): CodeBlock {
        val retVal = CodeBlock.builder()
        retVal.add("\n")
        retVal.add("xTiny: ${themeData.dimensions.xTiny},")
        retVal.add("\n")

        retVal.add("tiny: ${themeData.dimensions.tiny},")
        retVal.add("\n")

        retVal.add("small: ${themeData.dimensions.small},")
        retVal.add("\n")

        retVal.add("medium: ${themeData.dimensions.medium},")
        retVal.add("\n")

        retVal.add("large: ${themeData.dimensions.large},")
        retVal.add("\n")

        retVal.add("xLarge: ${themeData.dimensions.xLarge},")
        retVal.add("\n")

        retVal.add("xxLarge: ${themeData.dimensions.xxLarge},")
        retVal.add("\n")

        retVal.add("xxxLarge: ${themeData.dimensions.xxxLarge}")

        return retVal.build()
    }

    private fun spacings(): PropertySpec {
        val spacingsProperty = PropertySpec.builder(
            "spacings",
            TypeVariableName("Spacing")
        )
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)

        val spacingInitializer = CodeBlock
            .builder()
            .add("Spacing(")
            .add(spacingProperties())
            .add("\n")
            .add(")")
            .build()

        spacingsProperty.initializer(spacingInitializer)

        return spacingsProperty
            .build()
    }

    private fun spacingProperties(): CodeBlock {
        val retVal = CodeBlock.builder()
        retVal.add("\n")
        retVal.add("xTiny: ${themeData.spacings.xTiny},")
        retVal.add("\n")

        retVal.add("tiny: ${themeData.spacings.tiny},")
        retVal.add("\n")

        retVal.add("small: ${themeData.spacings.small},")
        retVal.add("\n")

        retVal.add("medium: ${themeData.spacings.medium},")
        retVal.add("\n")

        retVal.add("large: ${themeData.spacings.large},")
        retVal.add("\n")

        retVal.add("xLarge: ${themeData.spacings.xLarge},")
        retVal.add("\n")

        retVal.add("xxLarge: ${themeData.spacings.xxLarge},")
        retVal.add("\n")

        retVal.add("xxxLarge: ${themeData.spacings.xxxLarge}")

        return retVal.build()
    }

    private fun textStyles(): TypeSpec {
        val fontsClass = TypeSpec
            .structBuilder("Fonts")
            .addModifiers(Modifier.PUBLIC)

        val textStyles: TextStylesCollection = this.textStylesCollectionFrom(
            platformSpecific = themeData.iOS,
            fallback = themeData,
        ) ?: return fontsClass.build()

        textStyles
            .all
            .forEach {
            val fontProperty = PropertySpec.builder(
                it.key,
                TypeVariableName("Font")
            )

            val fontPropertyInitializer = CodeBlock
                .builder()
                .add("Font.system(size: ${it.value.fontSize}, weight: .${it.value.fontWeight.iOSName})")
                .build()

            fontProperty.initializer(fontPropertyInitializer)
            fontProperty.addModifiers(Modifier.STATIC)
            fontsClass.addProperty(fontProperty.build())
        }

        return fontsClass.build()
    }

    private fun colors(outDir: File) : TypeSpec {
        val createdColors = generateColors(outDir)
        return createColorClass(createdColors)
    }

    private fun createColorClass(colorNames: List<String>): TypeSpec {
        val colorsClass = TypeSpec
            .structBuilder("Colors")
            .addModifiers(Modifier.PUBLIC)

        colorNames.forEach { colorName ->
            val colorPropertyName = colorName.replaceFirstChar { it.lowercaseChar() }
            val colorProperty = PropertySpec.builder(
                colorPropertyName,
                    TypeVariableName("Color")
                )

            val colorPropertyInitializer = CodeBlock
                .builder()
                .add("Color(\"$colorName\")")
                .build()

            colorProperty.initializer(colorPropertyInitializer)
            colorProperty.addModifiers(Modifier.STATIC)
            colorsClass.addProperty(colorProperty.build())
        }

        return colorsClass.build()
    }

    private fun generateColors(outDir: File): List<String> {
        val colors = mutableListOf<String>()
        val assetFileName = "ThemeColors.xcassets"
        val assetFilePath = "${outDir.absolutePath}/$assetFileName"

        File(assetFilePath).mkdirs()

        themeData.lightColorScheme.all.forEach { currColorEntry ->
            val color = currColorEntry.value
            val iOSName = currColorEntry
                .key
                .replaceFirstChar { it.uppercaseChar() }
                .plus("Color")

            val darkModeColor = themeData.darkColorScheme.get(currColorEntry.key)
            AssetColorSetCreator()
                .setColor(color)
                .setDarkColor(darkModeColor)
                .create(assetFilePath, iOSName)

            colors.add(iOSName)

        }

        return colors
    }
}