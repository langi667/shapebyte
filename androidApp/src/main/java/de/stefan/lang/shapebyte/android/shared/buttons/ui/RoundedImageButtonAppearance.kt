package de.stefan.lang.shapebyte.android.shared.buttons.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.stefan.lang.shapebyte.android.LocalDimension

enum class RoundedImageButtonAppearance(val size: Dp) {
    Small((LocalDimension.current.medium - LocalDimension.current.xTiny).dp),
    Medium(LocalDimension.current.medium.dp),
    Large(LocalDimension.current.xLarge.dp),
}
