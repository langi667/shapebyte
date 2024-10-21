package de.stefan.lang.shapebyte.android

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import de.stefan.lang.shapebyte.android.designsystem.ui.WithTheme
import de.stefan.lang.shapebyte.android.designsystem.ui.color
import de.stefan.lang.shapebyte.android.designsystem.ui.toTextStyle

@Composable
@Suppress("MagicNumber")
fun ApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) = WithTheme { themeBase ->
    val colors = if (darkTheme) {
        darkColorScheme(
            primary = themeBase.colors.primary.darkModeColor.color,
            secondary = themeBase.colors.secondary.darkModeColor.color,
            background = themeBase.colors.background.darkModeColor.color,
        )
    } else {
        lightColorScheme(
            primary = themeBase.colors.primary.defaultColor.color,
            secondary = themeBase.colors.secondary.defaultColor.color,
            background = themeBase.colors.background.defaultColor.color,
        )
    }
    val typography = Typography(
        bodyMedium = themeBase.fonts.body.toTextStyle(),
        titleLarge = themeBase.fonts.title.toTextStyle(),
    )

    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp),
    )

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content,
    )
}
