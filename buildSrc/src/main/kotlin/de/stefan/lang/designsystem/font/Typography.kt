package de.stefan.lang.designsystem.font

import kotlin.reflect.full.declaredMemberProperties

data class Typography(
    val displayLarge: TextStyle = TextStyle (fontSize = 76, fontWeight = FontWeight.Black),
    val displayMedium: TextStyle = TextStyle(fontSize = 45, fontWeight = FontWeight.Bold),
    val displaySmall: TextStyle = TextStyle(fontSize = 36, fontWeight = FontWeight.Medium),

    val headlineLarge: TextStyle = TextStyle(fontSize = 36, fontWeight = FontWeight.Black),
    val headlineMedium: TextStyle = TextStyle(fontSize = 34, fontWeight = FontWeight.Bold),
    val headlineSmall: TextStyle = TextStyle(fontSize = 32, fontWeight = FontWeight.Medium),

    val titleLarge: TextStyle = TextStyle(fontSize = 28, fontWeight = FontWeight.Bold),
    val titleMedium: TextStyle = TextStyle(fontSize = 24, fontWeight = FontWeight.Bold),
    val titleSmall: TextStyle = TextStyle(fontSize = 21, fontWeight = FontWeight.Bold),

    val bodyLarge: TextStyle = TextStyle(fontSize = 20, fontWeight = FontWeight.Normal),
    val bodyMedium: TextStyle = TextStyle(fontSize = 19, fontWeight = FontWeight.Normal),
    val bodySmall: TextStyle = TextStyle(fontSize = 18, fontWeight = FontWeight.Normal),

    val labelLarge: TextStyle = TextStyle(fontSize = 16, fontWeight = FontWeight.Bold),
    val labelMedium: TextStyle = TextStyle(fontSize = 14, fontWeight = FontWeight.Bold),
    val labelSmall: TextStyle = TextStyle(fontSize = 12, fontWeight = FontWeight.Normal),
) {
    val textStyles: Map<String, TextStyle>
        get() {
            val properties = Typography::class.declaredMemberProperties
                .filter { it.returnType.classifier == TextStyle::class }

            val textStyles = HashMap<String,TextStyle>()
            properties.forEach { currTextStyle ->
                val textStyle = currTextStyle.get(this) as? TextStyle

                if (textStyle != null ) {
                    textStyles[currTextStyle.name] = textStyle
                }
                else {
                    println("Unable to create color from Property ${currTextStyle.name}")
                }
            }

            return textStyles
        }
}
