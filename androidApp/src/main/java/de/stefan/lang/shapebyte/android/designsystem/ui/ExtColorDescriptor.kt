package de.stefan.lang.shapebyte.android.designsystem.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import de.stefan.lang.designsystem.ColorDescriptor

@Composable
fun ColorDescriptor.Themed.color(): Color = withData { theme ->
    when (this) {
        is ColorDescriptor.Primary -> theme.current.colorScheme.primary
        is ColorDescriptor.Secondary -> theme.current.colorScheme.secondary
        is ColorDescriptor.Background -> theme.current.colorScheme.background
        is ColorDescriptor.InversePrimary -> theme.current.colorScheme.inversePrimary
    }
}
