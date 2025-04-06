package de.stefan.lang.shapebyte.android.designsystem.ui

import androidx.compose.material3.MaterialTheme
import de.stefan.lang.coreutils.logging.Logging
import de.stefan.lang.designsystem.Dimensions
import de.stefan.lang.designsystem.Spacings

data class ThemeData(
    val current: MaterialTheme,
    val dimensions: Dimensions,
    val spacings: Spacings,
    val animationDurations: AnimationDuration,
    val logger: Logging,
)
