package de.stefan.lang.shapebyte.android

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.stefan.lang.shapebyte.utils.designsystem.data.Dimension
import de.stefan.lang.shapebyte.utils.designsystem.data.Spacing

@Composable
@Suppress("MagicNumber")
fun ApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) { // TODO: dark mode color
        darkColorScheme(
            primary = Color(0xFFDC584D),
            secondary = Color(0xFF68BBC1),
            background = Color(0xFF3B6F89),
            inversePrimary = Color(0xFF138B4E),
        )
    } else {
        lightColorScheme(
            primary = Color(0xFFDC584D),
            secondary = Color(0xFF68BBC1),
            background = Color(0xFF3B6F89),
            inversePrimary = Color(0xFF138B4E),
        )
    }

    val typography = Typography(
        displayLarge = TextStyle(fontSize = 76.sp, fontWeight = FontWeight.Black),
        displayMedium = TextStyle(fontSize = 45.sp, fontWeight = FontWeight.Bold),
        displaySmall = TextStyle(fontSize = 36.sp, fontWeight = FontWeight.Medium),

        headlineLarge = TextStyle(fontSize = 36.sp, fontWeight = FontWeight.Black),
        headlineMedium = TextStyle(fontSize = 34.sp, fontWeight = FontWeight.Bold),
        headlineSmall = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Medium),

        titleLarge = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold),
        titleMedium = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
        titleSmall = TextStyle(fontSize = 21.sp, fontWeight = FontWeight.Bold),

        bodyLarge = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Normal),
        bodyMedium = TextStyle(fontSize = 19.sp, fontWeight = FontWeight.Normal),
        bodySmall = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Normal),

        labelLarge = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
        labelMedium = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold),
        labelSmall = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal),
    )

    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(16.dp),
        extraLarge = RoundedCornerShape(48.dp),
    )

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content,
    )
}

object LocalSpacing {
    val current: Spacing by lazy {
        Spacing(
            xTiny = 4,
            tiny = 8,
            small = 16,
            medium = 32,
            large = 48,
            xLarge = 64,
            xxLarge = 84,
            xxxLarge = 128,
        )
    }
}

object LocalDimension {
    val current: Dimension by lazy {
        Dimension(
            xTiny = 16,
            tiny = 24,
            small = 36,
            medium = 72,
            large = 128,
            xLarge = 192,
            xxLarge = 256,
            xxxLarge = 320,
        )
    }
}

// TODO:pass in With function
object LocalAnimationDuration {
    const val short: Long = 350
    const val long: Long = 700
}
