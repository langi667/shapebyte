package de.stefan.lang.shapebyte.android.designsystem.ui

import androidx.compose.material3.MaterialTheme
import de.stefan.lang.shapebyte.utils.designsystem.data.Dimension
import de.stefan.lang.shapebyte.utils.designsystem.data.Spacing
import de.stefan.lang.shapebyte.utils.logging.Logging

data class ThemeData(
    val current: MaterialTheme,
    val dimensions: Dimension,
    val spacings: Spacing,
    val animationDurations: AnimationDuration,
    val logger: Logging,
)
