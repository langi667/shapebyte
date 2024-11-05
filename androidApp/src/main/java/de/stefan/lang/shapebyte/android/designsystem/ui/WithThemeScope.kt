package de.stefan.lang.shapebyte.android.designsystem.ui

import androidx.compose.runtime.Composable
import de.stefan.lang.shapebyte.designsystem.ui.ThemeProvider

@Composable
fun WithTheme(content: @Composable (theme: ThemeProvider) -> Unit) {
    content(ThemeProvider)
}
