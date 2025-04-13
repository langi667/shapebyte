package de.stefan.lang.shapebyte.android.shared.buttons.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.stefan.lang.designsystem.theme.ThemeAdditions

enum class RoundedImageButtonAppearance(val size: Dp) {
    Small((ThemeAdditions.dimensions.medium - ThemeAdditions.dimensions.xTiny).dp),
    Medium(ThemeAdditions.dimensions.medium.dp),
    Large(ThemeAdditions.dimensions.xLarge.dp),
}
