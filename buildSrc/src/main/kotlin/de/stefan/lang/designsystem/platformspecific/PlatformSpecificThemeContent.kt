package de.stefan.lang.designsystem.platformspecific
import de.stefan.lang.designsystem.ThemeContent
import de.stefan.lang.designsystem.animation.AnimationDurations
import de.stefan.lang.designsystem.font.TextStyles

import de.stefan.lang.designsystem.color.ColorScheme
import de.stefan.lang.designsystem.dimension.Dimensions
import de.stefan.lang.designsystem.shapes.Shapes
import de.stefan.lang.designsystem.spacing.Spacings

data class PlatformSpecificThemeContent(
    val platform: Platform,
    override val textStyles: TextStyles? = null,
    override val animationDurations: AnimationDurations? = null,
    override val lightColorScheme: ColorScheme? = null,
    override val darkColorScheme: ColorScheme? = null,
    override val shapes: Shapes? = null,
    override val dimensions: Dimensions? = null,
    override val spacings: Spacings? = null,
): ThemeContent