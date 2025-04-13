package de.stefan.lang.shapebyte.android.shared.buttons.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.stefan.lang.shapebyte.android.designsystem.ui.ThemeData

enum class RoundedImageButtonAppearance(val size: Dp) {
    Small((ThemeData.dimensions.medium - ThemeData.dimensions.xTiny).dp),
    Medium(ThemeData.dimensions.medium.dp),
    Large(ThemeData.dimensions.xLarge.dp),
}
