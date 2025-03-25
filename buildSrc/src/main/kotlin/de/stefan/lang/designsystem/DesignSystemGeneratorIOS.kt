package de.stefan.lang.designsystem

import de.stefan.lang.designsystem.color.Color
import de.stefan.lang.designsystem.font.TextStylesCollection
import de.stefan.lang.designsystem.color.ios.AssetColorSetBuilder

import io.outfoxx.swiftpoet.CodeBlock
import io.outfoxx.swiftpoet.FileSpec
import io.outfoxx.swiftpoet.Modifier
import io.outfoxx.swiftpoet.PropertySpec
import io.outfoxx.swiftpoet.TypeSpec
import io.outfoxx.swiftpoet.TypeVariableName
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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
            .build()

        val file = FileSpec.builder("Theme")
            .addType(themeClass)
            .addImport("SwiftUI")
            .addImport("shared")
            .build()

        file.writeTo(outputFile)

        val testColor = AssetColorSetBuilder()
            .setColor(Color("0xff00ff00"))
            .setDarkColor(Color("0xff0000ff"))
            .build()

        if (testColor != null) {
            val json = Json { prettyPrint = true } // prettyPrint for formatted JSON
            val string = json.encodeToString(testColor)

            val outFile = File("/Users/stefanlang/dev/ShapeByte/iosApp/iosApp/generated/theme/ThemeColors.xcassets/Test.colorset/Contents.json")
            outFile.writeText(string)
        }

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
}


/*
public struct Theme {
    public static let spacings = Spacing(
        xTiny: 4,
        tiny: 8,
        small: 16,
        medium: 32,
        large: 48,
        xLarge: 64,
        xxLarge: 84,
        xxxLarge: 128
    )

    public static let dimensions = Dimension(
        xTiny: 16,
        tiny: 24,
        small: 36,
        medium: 72,
        large: 128,
        xLarge: 192,
        xxLarge: 256,
        xxxLarge: 320
    )

    public struct Fonts {
        static let displayLarge: Font = Font.system(size: 76, weight: .black)
        static let displayMedium: Font = Font.system(size: 45, weight: .bold)
        static let displaySmall: Font = Font.system(size: 40, weight: .bold)

        static let headlineLarge: Font = Font.system(size: 36, weight: .black)
        static let headlineMedium: Font = Font.system(size: 34, weight: .bold)
        static let headlineSmall: Font = Font.system(size: 32, weight: .medium)

        static let titleLarge: Font = Font.system(size: 28, weight: .bold)
        static let titleMedium: Font = Font.system(size: 24, weight: .bold)
        static let titleSmall: Font = Font.system(size: 21, weight: .bold)

        static let bodyLarge: Font = Font.system(size: 20, weight: .regular)
        static let bodyMedium: Font = Font.system(size: 19, weight: .regular)
        static let bodySmall: Font = Font.system(size: 18, weight: .regular)

        static let labelLarge: Font = Font.system(size: 16, weight: .bold)
        static let labelmedium: Font = Font.system(size: 14, weight: .bold)
        static let labelSmall: Font = Font.system(size: 14, weight: .semibold)
    }

    public struct Colors {
        static let backgroundColor: Color = Color("BackgroundColor")
        static let primaryColor: Color = Color("PrimaryColor")
        static let secondaryColor: Color = Color("SecondaryColor")
        static let inversePrimaryColor: Color = Color("InversePrimaryColor")
    }

    public struct Shapes {
        static let small: CGFloat = 4.0
        static let medium: CGFloat = 16.0
        static let large: CGFloat = 32
        static let xLarge: CGFloat = 48
    }

    // TODO: also add to Android
    public struct AnimationDuration {
        static let short: TimeInterval = 0.3
        static let medium: TimeInterval = 0.5
        static let long: TimeInterval = 0.75
    }
}*/