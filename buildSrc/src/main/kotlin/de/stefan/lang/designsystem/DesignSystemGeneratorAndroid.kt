package de.stefan.lang.designsystem

import com.squareup.kotlinpoet.*
import java.io.File
import de.stefan.lang.designsystem.color.ColorScheme

class DesignSystemGeneratorAndroid {

    private val composableClass = ClassName("androidx.compose.runtime", "Composable")
    private val composableAnnotation = AnnotationSpec.builder(
        composableClass
    ).build()

    private val themeData: ThemeData = ThemeData

    fun generate(outputDir: File) {
        val themeName = "ShapeByteTheme"
        val packageName = "de.stefan.lang.designsystem.theme"

        val themeBuilder = themeFunctionDeclaration(themeName)
            .addCode(
                colorCode()
            )
            .addCode("\n")
            .addCode(typographyCode())
            // TODO: this must come from Theme Data !
            .addStatement(" val shapes = Shapes(\n" +
                    "        small = RoundedCornerShape(4.dp),\n" +
                    "        medium = RoundedCornerShape(4.dp),\n" +
                    "        large = RoundedCornerShape(16.dp),\n" +
                    "        extraLarge = RoundedCornerShape(48.dp),\n" +
                    "    )\n" +
                    "\n" +
                    "    MaterialTheme(\n" +
                    "        colorScheme = colors,\n" +
                    "        typography = typography,\n" +
                    "        shapes = shapes,\n" +
                    "        content = content,\n" +
                    "    )")



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

        fileSpec.writeTo(outputDir)
        println("DesignSystem Android Theme generated successfully! to ${outputDir.absolutePath}")
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
                    colorScheme = themeData.darkColorScheme
                )
            )
            .add("\n")
            .nextControlFlow("else")
            .add(
                codeBlock = colorSchemeCode(
                    name = "lightColorScheme",
                    colorScheme = themeData.lightColorScheme
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

    private fun typographyCode() : CodeBlock {
        val builder =  CodeBlock
            .builder()

        builder.addStatement( "val typography = Typography(")
        builder.indent()

        themeData.typography.textStyles.forEach { currTextStyle ->
            builder.addStatement(
                "${currTextStyle.key} = TextStyle(fontSize = ${currTextStyle.value.fontSize}.sp, fontWeight = FontWeight.${currTextStyle.value.fontWeight.name}),"
            )
        }

        builder.unindent()
        builder.addStatement( ")")

        return builder.build()
    }
}