package de.stefan.lang.shapebyte.android.designsystem.ui

import androidx.compose.runtime.Composable
import de.stefan.lang.shapebyte.designsystem.ui.ThemeProvider
import de.stefan.lang.shapebyte.di.DPI
import de.stefan.lang.shapebyte.utils.logging.Logging

@Composable
fun WithTheme(content: @Composable (theme: ThemeProvider, logger: Logging) -> Unit) {
    content(ThemeProvider, DPI.logger())
}
