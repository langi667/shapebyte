package de.stefan.lang.designsystem

import com.squareup.kotlinpoet.*
import java.io.File
import de.stefan.lang.designsystem.color.ColorScheme
import de.stefan.lang.designsystem.font.TextStylesCollection
import de.stefan.lang.designsystem.platformspecific.Platform

class DesignSystemThemeGeneratorAndroid {
    private val composableClass = ClassName("androidx.compose.runtime", "Composable")
    private val composableAnnotation = AnnotationSpec.builder(
        composableClass
    ).build()

    private val themeDataProvider: ThemeDataProvider = ThemeDataProvider(
        Platform.Android,
        ThemeData()
    )

    fun generate(outputFile: File) {
        generateTheme(outputFile)
        println("DesignSystem Android Theme generated successfully! to ${outputFile.absolutePath}")
    }

    private fun generateTheme(outputFile: File) {
        val themeName = "ShapeByteTheme"
        val packageName = "de.stefan.lang.designsystem.theme"

        val themeBuilder = themeFunctionDeclaration(themeName)
            .addCode(
                colorCode()
            )
            .addCode("\n")
            .addCode(textStyles())
            .addCode("\n")
            .addCode(shapes())
            .addCode("\n")
            .addCode(materialThemeDeclaration())

        val fileSpec = FileSpec.builder(packageName, themeName)
            .addImport("androidx.compose.foundation", "isSystemInDarkTheme")
            .addImport("androidx.compose.material3", "darkColorScheme")
            .addImport("androidx.compose.material3", "lightColorScheme")
            .addImport("androidx.compose.material3", "Typography")
            .addImport("androidx.compose.ui.text", "TextStyle")
            .addImport("androidx.compose.ui.unit", "sp")
            .addImport("androidx.compose.ui.text.font", "FontWeight")
            .addImport("androidx.compose.material3", "Shapes")
            .addImport("androidx.compose.foundation.shape", "RoundedCornerShape")
            .addImport("androidx.compose.ui.unit", "dp")
            .addImport("androidx.compose.material3", "MaterialTheme")
            .addImport("androidx.compose.ui.graphics", "Color")
            .addFunction(themeBuilder.build())
            .build()

        fileSpec.writeTo(outputFile)
    }

    private fun materialThemeDeclaration(): CodeBlock  {
        return CodeBlock
            .builder()
            .addStatement(
                "MaterialTheme(\n" +
                        "  colorScheme = colors,\n" +
                        "  typography = typography,\n" +
                        "  shapes = shapes,\n" +
                        "  content = content,\n" +
                        ")"
            )
            .build()
    }

    private fun themeFunctionDeclaration(
        themeName: String,
    ): FunSpec.Builder{
        val composableLambdaType = LambdaTypeName.get(
            returnType = UNIT
        ).copy(
            annotations = listOf(AnnotationSpec.builder(composableClass).build())
        )

        return FunSpec.builder(themeName)
            .addParameter(
                ParameterSpec.builder("darkTheme", Boolean::class)
                    .defaultValue("isSystemInDarkTheme()")
                    .build()
            )
            .addParameter(ParameterSpec.builder("content", composableLambdaType)
                .build())
            .addAnnotation(composableAnnotation)
            .addAnnotation(
                AnnotationSpec.builder(Suppress::class)
                    .addMember("%S", "MagicNumber")
                    .build()
            )
    }

    private fun colorCode(): CodeBlock {
        return CodeBlock
            .builder()
            .beginControlFlow("val colors = if (darkTheme)")
            .add(
                codeBlock = colorSchemeCode(
                    name = "darkColorScheme",
                    colorScheme = themeDataProvider.darkColorScheme
                )
            )
            .add("\n")
            .nextControlFlow("else")
            .add(
                codeBlock = colorSchemeCode(
                    name = "lightColorScheme",
                    colorScheme = themeDataProvider.lightColorScheme
                )
            )
            .add("\n")
            .unindent()
            .endControlFlow()
            .build()
    }

    private fun colorSchemeCode(
        name: String,
        colorScheme: ColorScheme
    ): CodeBlock {
        val colorSchemeCall = CodeBlock
            .builder()
            .add(
                CodeBlock
                    .builder()
                    .indent()
                    .addStatement("$name(")
                    .indent()
                    .add(colorSchemeToStatement(colorScheme))
                    .unindent()
                    .add(")")
                    .build()
            )
            .build()

        return colorSchemeCall
    }

    private fun colorSchemeToStatement(colorScheme: ColorScheme): CodeBlock {
        val builder = CodeBlock.builder()

        colorScheme.colors.forEach {
            builder.addStatement(
                "${it.key} = Color(${it.value}),"
            )
        }

        return builder.build()
    }

    private fun textStyles() : CodeBlock {
        val builder =  CodeBlock
            .builder()

        val textStyles = themeDataProvider.textStyles as TextStylesCollection

        builder.addStatement( "val typography = Typography(")
        builder.indent()

        textStyles.all.forEach { currTextStyle ->
            builder.addStatement(
                "${currTextStyle.key} = TextStyle(fontSize = ${currTextStyle.value.fontSize}.sp, fontWeight = FontWeight.${currTextStyle.value.fontWeight.name}),"
            )
        }

        builder.unindent()
        builder.addStatement( ")")

        return builder.build()
    }

    private fun shapes() : CodeBlock {
        val builder =  CodeBlock
            .builder()

        builder.addStatement( "val shapes = Shapes(")
        builder.indent()

        themeDataProvider.shapes.roundedCorners.all.forEach { currRoundedCorner ->
            builder.addStatement(
                "${currRoundedCorner.key} = RoundedCornerShape(${currRoundedCorner.value.radius}.dp),"
            )
        }

        builder.unindent()
        builder.addStatement( ")")

        return builder.build()
    }
}

/*
class DesignSystemGeneratorAndroid: DesignSystemGenerating {
    private val composableClass = ClassName("androidx.compose.runtime", "Composable")
    private val composableAnnotation = AnnotationSpec.builder(
        composableClass
    ).build()

    private val themeDataProvider: ThemeDataProvider = ThemeDataProvider(
        Platform.Android,
        ThemeData()
    )

    override fun generate(outputFile: File) {
        generateTheme(outputFile)
        generateThemeAdditions(outputFile)
        println("DesignSystem Android Theme generated successfully! to ${outputFile.absolutePath}")
    }

    private fun generateThemeAdditions(outputFile: File) {
        val objectName = "ThemeAdditions"
        val packageName = "de.stefan.lang.designsystem.theme"

        val loggerProperty = PropertySpec.builder("logger", ClassName("de.stefan.lang.coreutils.logging", "Logger"))
            .getter(FunSpec.getterBuilder().addStatement("return SharedModule.logger()").build())
            .build()

        val animationDurationsProperty = PropertySpec.builder("animationDurations", ClassName("de.stefan.lang.shapebyte.android.designsystem.ui", "AnimationDuration"))
            .getter(FunSpec.getterBuilder().addStatement("return SharedModule.logger()").build())
            .build()

        val themeAdditions = TypeSpec.objectBuilder(objectName)
            .addProperty(loggerProperty)
            .build()


        val fileSpec = FileSpec.builder(packageName, objectName)
            .addType(themeAdditions)
            .addImport("import de.stefan.lang.coreutils.logging", "Logger")
            .addImport("de.stefan.lang.designsystem", "Dimensions")
            .addImport("de.stefan.lang.designsystem", "Spacings")
            .addImport("import de.stefan.lang.shapebyte", "SharedModule")
            .build()

        fileSpec.writeTo(outputFile)
    }

    private fun generateTheme(outputFile: File) {
        val themeName = "ShapeByteTheme"
        val packageName = "de.stefan.lang.designsystem.theme"

        val themeBuilder = themeFunctionDeclaration(themeName)
            .addCode(
                colorCode()
            )
            .addCode("\n")
            .addCode(textStyles())
            .addCode("\n")
            .addCode(shapes())
            .addCode("\n")
            .addCode(materialThemeDeclaration())

        val fileSpec = FileSpec.builder(packageName, themeName)
            .addImport("androidx.compose.foundation", "isSystemInDarkTheme")
            .addImport("androidx.compose.material3", "darkColorScheme")
            .addImport("androidx.compose.material3", "lightColorScheme")
            .addImport("androidx.compose.material3", "Typography")
            .addImport("androidx.compose.ui.text", "TextStyle")
            .addImport("androidx.compose.ui.unit", "sp")
            .addImport("androidx.compose.ui.text.font", "FontWeight")
            .addImport("androidx.compose.material3", "Shapes")
            .addImport("androidx.compose.foundation.shape", "RoundedCornerShape")
            .addImport("androidx.compose.ui.unit", "dp")
            .addImport("androidx.compose.material3", "MaterialTheme")
            .addImport("androidx.compose.ui.graphics", "Color")
            .addFunction(themeBuilder.build())
            .build()

        fileSpec.writeTo(outputFile)
    }

    private fun materialThemeDeclaration(): CodeBlock  {
        return CodeBlock
            .builder()
            .addStatement(
                "MaterialTheme(\n" +
                "  colorScheme = colors,\n" +
                "  typography = typography,\n" +
                "  shapes = shapes,\n" +
                "  content = content,\n" +
                ")"
            )
            .build()
    }

    private fun themeFunctionDeclaration(
        themeName: String,
    ): FunSpec.Builder{
        val composableLambdaType = LambdaTypeName.get(
            returnType = UNIT
        ).copy(
            annotations = listOf(AnnotationSpec.builder(composableClass).build())
        )

        return FunSpec.builder(themeName)
            .addParameter(
                ParameterSpec.builder("darkTheme", Boolean::class)
                    .defaultValue("isSystemInDarkTheme()")
                    .build()
            )
            .addParameter(ParameterSpec.builder("content", composableLambdaType)
                .build())
            .addAnnotation(composableAnnotation)
            .addAnnotation(
                AnnotationSpec.builder(Suppress::class)
                    .addMember("%S", "MagicNumber")
                    .build()
            )
    }

    private fun colorCode(): CodeBlock {
        return CodeBlock
            .builder()
            .beginControlFlow("val colors = if (darkTheme)")
            .add(
                codeBlock = colorSchemeCode(
                    name = "darkColorScheme",
                    colorScheme = themeDataProvider.darkColorScheme
                )
            )
            .add("\n")
            .nextControlFlow("else")
            .add(
                codeBlock = colorSchemeCode(
                    name = "lightColorScheme",
                    colorScheme = themeDataProvider.lightColorScheme
                )
            )
            .add("\n")
            .unindent()
            .endControlFlow()
            .build()
    }

    private fun colorSchemeCode(
        name: String,
        colorScheme: ColorScheme
    ): CodeBlock {
        val colorSchemeCall = CodeBlock
            .builder()
            .add(
                CodeBlock
                    .builder()
                    .indent()
                    .addStatement("$name(")
                    .indent()
                    .add(colorSchemeToStatement(colorScheme))
                    .unindent()
                    .add(")")
                    .build()
            )
            .build()

        return colorSchemeCall
    }

    private fun colorSchemeToStatement(colorScheme: ColorScheme): CodeBlock {
        val builder = CodeBlock.builder()

        colorScheme.colors.forEach {
            builder.addStatement(
                "${it.key} = Color(${it.value}),"
            )
        }

        return builder.build()
    }

    private fun textStyles() : CodeBlock {
        val builder =  CodeBlock
            .builder()

        val textStyles = themeDataProvider.textStyles as TextStylesCollection

        builder.addStatement( "val typography = Typography(")
        builder.indent()

        textStyles.all.forEach { currTextStyle ->
            builder.addStatement(
                "${currTextStyle.key} = TextStyle(fontSize = ${currTextStyle.value.fontSize}.sp, fontWeight = FontWeight.${currTextStyle.value.fontWeight.name}),"
            )
        }

        builder.unindent()
        builder.addStatement( ")")

        return builder.build()
    }

    private fun shapes() : CodeBlock {
        val builder =  CodeBlock
            .builder()

        builder.addStatement( "val shapes = Shapes(")
        builder.indent()

        themeDataProvider.shapes.roundedCorners.all.forEach { currRoundedCorner ->
            builder.addStatement(
                "${currRoundedCorner.key} = RoundedCornerShape(${currRoundedCorner.value.radius}.dp),"
            )
        }

        builder.unindent()
        builder.addStatement( ")")

        return builder.build()
    }
}
 */