package de.stefan.lang.shapebyte.android.designsystem.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import de.stefan.lang.designsystem.contract.ColorDescriptor

@Composable
fun ColorDescriptor.Themed.color(): Color = when (this) {
    is ColorDescriptor.Primary -> MaterialTheme.colorScheme.primary
    is ColorDescriptor.Secondary -> MaterialTheme.colorScheme.secondary
    is ColorDescriptor.Background -> MaterialTheme.colorScheme.background
    is ColorDescriptor.InversePrimary -> MaterialTheme.colorScheme.inversePrimary
}
