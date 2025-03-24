package de.stefan.lang.designsystem

import de.stefan.lang.designsystem.color.ColorScheme
import de.stefan.lang.designsystem.color.Color
import de.stefan.lang.designsystem.font.FontWeight
import de.stefan.lang.designsystem.font.TextStyle
import de.stefan.lang.designsystem.font.Typography
import de.stefan.lang.designsystem.shapes.RoundedCorners
import de.stefan.lang.designsystem.shapes.Shape
import de.stefan.lang.designsystem.shapes.Shapes

object ThemeData {
    val lightColorScheme = ColorScheme(
        primary = Color("0xFFDC584D"),
        secondary = Color("0xFF68BBC1"),
        background = Color("0xFF3B6F89"),
        inversePrimary = Color("0xFF138B4E"),
    )

    val darkColorScheme = ColorScheme(
        primary = Color("0xFFDC584D"),
        secondary = Color("0xFF68BBC1"),
        background = Color("0xFF3B6F89"),
        inversePrimary = Color("0xFF138B4E"),
    )

    val typography = Typography(
        displayLarge = TextStyle(fontSize = 76, fontWeight = FontWeight.Black),
        displayMedium = TextStyle(fontSize = 45, fontWeight = FontWeight.Bold),
        displaySmall = TextStyle(fontSize = 36, fontWeight = FontWeight.Medium),

        headlineLarge = TextStyle(fontSize = 36, fontWeight = FontWeight.Black),
        headlineMedium = TextStyle(fontSize = 34, fontWeight = FontWeight.Bold),
        headlineSmall = TextStyle(fontSize = 32, fontWeight = FontWeight.Medium),

        titleLarge = TextStyle(fontSize = 28, fontWeight = FontWeight.Bold),
        titleMedium = TextStyle(fontSize = 24, fontWeight = FontWeight.Bold),
        titleSmall = TextStyle(fontSize = 21, fontWeight = FontWeight.Bold),

        bodyLarge = TextStyle(fontSize = 20, fontWeight = FontWeight.Normal),
        bodyMedium = TextStyle(fontSize = 19, fontWeight = FontWeight.Normal),
        bodySmall = TextStyle(fontSize = 18, fontWeight = FontWeight.Normal),

        labelLarge = TextStyle(fontSize = 16, fontWeight = FontWeight.Bold),
        labelMedium = TextStyle(fontSize = 14, fontWeight = FontWeight.Bold),
        labelSmall = TextStyle(fontSize = 12, fontWeight = FontWeight.Normal),
    )

    val shapes = Shapes(
        roundedCorners = RoundedCorners(
            small = Shape.RoundedCorner(radius = 4),
            medium = Shape.RoundedCorner(radius = 4),
            large = Shape.RoundedCorner(radius = 16),
            extraLarge = Shape.RoundedCorner(radius = 48),
        )
    )
}
