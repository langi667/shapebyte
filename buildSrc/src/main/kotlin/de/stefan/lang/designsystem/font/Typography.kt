package de.stefan.lang.designsystem.font
import de.stefan.lang.designsystem.core.PropertyReader

data class Typography(
    override val displayLarge: TextStyle = TextStyle (fontSize = 76, fontWeight = FontWeight.Black),
    override val displayMedium: TextStyle = TextStyle(fontSize = 45, fontWeight = FontWeight.Bold),
    override val displaySmall: TextStyle = TextStyle(fontSize = 36, fontWeight = FontWeight.Medium),

    override val headlineLarge: TextStyle = TextStyle(fontSize = 36, fontWeight = FontWeight.Black),
    override val headlineMedium: TextStyle = TextStyle(fontSize = 34, fontWeight = FontWeight.Bold),
    override val headlineSmall: TextStyle = TextStyle(fontSize = 32, fontWeight = FontWeight.Medium),

    override val titleLarge: TextStyle = TextStyle(fontSize = 28, fontWeight = FontWeight.Bold),
    override val titleMedium: TextStyle = TextStyle(fontSize = 24, fontWeight = FontWeight.Bold),
    override val titleSmall: TextStyle = TextStyle(fontSize = 21, fontWeight = FontWeight.Bold),

    override val bodyLarge: TextStyle = TextStyle(fontSize = 20, fontWeight = FontWeight.Normal),
    override val bodyMedium: TextStyle = TextStyle(fontSize = 19, fontWeight = FontWeight.Normal),
    override val bodySmall: TextStyle = TextStyle(fontSize = 18, fontWeight = FontWeight.Normal),

    override val labelLarge: TextStyle = TextStyle(fontSize = 16, fontWeight = FontWeight.Bold),
    override val labelMedium: TextStyle = TextStyle(fontSize = 14, fontWeight = FontWeight.Bold),
    override val labelSmall: TextStyle = TextStyle(fontSize = 12, fontWeight = FontWeight.Normal),
): TextStylesCollection {
    override val all: Map<String, TextStyle>
        get() {
            val textStyles = PropertyReader.read<TextStyle, Typography>(this)
            return textStyles.entries.sortedWith(
                compareByDescending<Map.Entry<String, TextStyle>> { it.value.fontSize }
                    .thenByDescending { it.value.fontWeight.ordinal }
            ).associate { it.key to it.value }

        }
}
