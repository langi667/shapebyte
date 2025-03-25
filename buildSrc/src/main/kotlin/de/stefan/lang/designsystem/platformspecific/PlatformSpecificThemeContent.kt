package de.stefan.lang.designsystem.platformspecific
import de.stefan.lang.designsystem.animation.AnimationDurations
import de.stefan.lang.designsystem.font.TextStyles
import de.stefan.lang.designsystem.font.TextStylesProviding
import de.stefan.lang.designsystem.animation.AnimationDurationsProviding

data class PlatformSpecificThemeContent(
    override val textStyles: TextStyles,
    override val animationDurations: AnimationDurations? = null,
): TextStylesProviding, AnimationDurationsProviding