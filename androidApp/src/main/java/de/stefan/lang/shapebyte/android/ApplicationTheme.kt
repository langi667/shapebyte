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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import de.stefan.lang.shapebyte.android.designsystem.ui.toTextStyle
import de.stefan.lang.shapebyte.android.workout.item.ui.WithTheme

@Composable
@Suppress("MagicNumber")
fun ApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) = WithTheme { themeBase ->
    val context = LocalContext.current
    val colors = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFFBB86FC),
            secondary = Color(0xFF03DAC5),
            tertiary = Color(0xFF3700B3),
            background = Color(context.getColor(R.color.background)),
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC5),
            tertiary = Color(0xFF3700B3),
            background = Color(context.getColor(R.color.background)),
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
