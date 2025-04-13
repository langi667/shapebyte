package de.stefan.lang.designsystem

import de.stefan.lang.designsystem.animation.AnimationDurations
import de.stefan.lang.designsystem.color.ColorSchemeProviding
import de.stefan.lang.designsystem.color.ColorScheme
import de.stefan.lang.designsystem.color.Color
import de.stefan.lang.designsystem.font.TextStyles
import de.stefan.lang.designsystem.font.EmptyTextStyles
import de.stefan.lang.designsystem.font.TextStylesProviding
import de.stefan.lang.designsystem.font.TextStyle
import de.stefan.lang.designsystem.font.Typography
import de.stefan.lang.designsystem.font.FontWeight
import de.stefan.lang.designsystem.shapes.RoundedCorners
import de.stefan.lang.designsystem.shapes.Shape
import de.stefan.lang.designsystem.shapes.Shapes
import de.stefan.lang.designsystem.shapes.ShapesProviding
import de.stefan.lang.designsystem.dimension.Dimensions
import de.stefan.lang.designsystem.dimension.DimensionsProviding
import de.stefan.lang.designsystem.platformspecific.Platform
import de.stefan.lang.designsystem.platformspecific.PlatformSpecificThemeContent
import de.stefan.lang.designsystem.spacing.Spacings
import de.stefan.lang.designsystem.spacing.SpacingsProviding
import de.stefan.lang.designsystem.animation.AnimationDurationsProviding

interface ThemeContent :
    TextStylesProviding,
    AnimationDurationsProviding,
    ColorSchemeProviding,
    ShapesProviding,
    DimensionsProviding,
    SpacingsProviding

// TODO: parse from YAML or JSON
class ThemeData : ThemeContent {
    override val lightColorScheme = ColorScheme(
        primary = Color("0xFFDC584D"),
        secondary = Color("0xFF68BBC1"),
        background = Color("0xFF3B6F89"),
        inversePrimary = Color("0xFF138B4E"),
    )

    override val darkColorScheme = ColorScheme(
        primary = Color("0xFFDC584D"),
        secondary = Color("0xFF68BBC1"),
        background = Color("0xFF3B6F89"),
        inversePrimary = Color("0xFF138B4E"),
    )

    override val shapes = Shapes(
        roundedCorners = RoundedCorners(
            small = Shape.RoundedCorner(radius = 4),
            medium = Shape.RoundedCorner(radius = 16),
            large = Shape.RoundedCorner(radius = 32),
            extraLarge = Shape.RoundedCorner(radius = 48),
        )
    )

    override val dimensions: Dimensions = Dimensions(
        xTiny = 16,
        tiny = 24,
        small = 36,
        medium = 72,
        large = 128,
        xLarge = 192,
        xxLarge = 256,
        xxxLarge = 320,
    )

    override val spacings: Spacings = Spacings(
        xTiny = 4,
        tiny = 8,
        small = 16,
        medium = 32,
        large = 48,
        xLarge = 64,
        xxLarge = 84,
        xxxLarge = 128,
    )

    override val textStyles: TextStyles = EmptyTextStyles

    override val animationDurations: AnimationDurations = AnimationDurations(
        short = 0.3,
        long = 0.75,
    )

    val android = PlatformSpecificThemeContent(
        platform = Platform.Android,
        animationDurations = AnimationDurations(
            short = 300.0,
            long = 750.0,
        ),
        textStyles = Typography(
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
    )

    val iOS = PlatformSpecificThemeContent(
        platform = Platform.iOS,
        textStyles = Typography(
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
    )
}
