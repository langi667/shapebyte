package de.stefan.lang.shapebyte.android.designsystem.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalInspectionMode
import de.stefan.lang.shapebyte.android.ApplicationTheme
import de.stefan.lang.shapebyte.android.LocalDimension
import de.stefan.lang.shapebyte.android.LocalSpacing
import de.stefan.lang.shapebyte.di.DPI
import de.stefan.lang.shapebyte.utils.designsystem.data.Dimension
import de.stefan.lang.shapebyte.utils.designsystem.data.Spacing
import de.stefan.lang.shapebyte.utils.logging.Logging
import de.stefan.lang.shapebyte.utils.mocks.SilentLogger

@Composable
fun With(
    content: @Composable (dimension: Dimension, spacing: Spacing, logger: Logging) -> Unit,
) {

    // TODO: check if in Preview Mode, DPI is not available in inspection mode
    // TODO: handle in preview for DPI, also for iOS
    val logger = if (LocalInspectionMode.current) {
        SilentLogger()
    } else {
        DPI.logger()
    }

    ApplicationTheme {
        content(LocalDimension.current, LocalSpacing.current, logger)
    }
}
