package de.stefan.lang.shapebyte.android.designsystem.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import de.stefan.lang.shapebyte.SharedModule
import de.stefan.lang.shapebyte.android.LocalAnimationDuration
import de.stefan.lang.shapebyte.android.LocalDimension
import de.stefan.lang.shapebyte.android.LocalSpacing
import de.stefan.lang.shapebyte.android.ShapeByteTheme

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
    val logger = SharedModule.logger()

    val themeData = remember {
        SharedModule.appInfo()
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
