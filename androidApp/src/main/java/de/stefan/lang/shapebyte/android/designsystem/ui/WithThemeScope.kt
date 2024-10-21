package de.stefan.lang.shapebyte.android.designsystem.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import de.stefan.lang.shapebyte.designsystem.ui.ThemeProvider

@Composable
fun WithTheme(content: @Composable (theme: ThemeProvider) -> Unit) {
    val theme: ThemeProvider = remember { ThemeProvider() }
    content(theme)
}
