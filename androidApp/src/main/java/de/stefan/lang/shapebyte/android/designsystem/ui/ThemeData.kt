package de.stefan.lang.shapebyte.android.designsystem.ui

import androidx.compose.material3.MaterialTheme
import de.stefan.lang.coreutils.logging.Logging
import de.stefan.lang.designsystem.Dimension
import de.stefan.lang.designsystem.Spacing

data class ThemeData(
    val current: MaterialTheme,
    val dimensions: Dimension,
    val spacings: Spacing,
    val animationDurations: AnimationDuration,
    val logger: Logging,
)
