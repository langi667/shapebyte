package de.stefan.lang.shapebyte.android.designsystem.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import de.stefan.lang.shapebyte.android.LocalAnimationDuration
import de.stefan.lang.shapebyte.android.LocalDimension
import de.stefan.lang.shapebyte.android.LocalSpacing
import de.stefan.lang.shapebyte.android.ShapeByteTheme
import de.stefan.lang.shapebyte.di.DPI

@Composable
fun With(
    content: @Composable (theme: ThemeData) -> Unit,
) {
    ShapeByteTheme {
        withData(content)
    }
}

@Composable
fun <T> withData(
    content: @Composable (theme: ThemeData) -> T,
): T {
    val logger = DPI.logger()

    val themeData = remember {
        DPI.appInfo()
        ThemeData(
            current = MaterialTheme,
            dimensions = LocalDimension.current,
            spacings = LocalSpacing.current,
            animationDurations = LocalAnimationDuration.current,
            logger = logger,
        )
    }

    return content(themeData)
}
