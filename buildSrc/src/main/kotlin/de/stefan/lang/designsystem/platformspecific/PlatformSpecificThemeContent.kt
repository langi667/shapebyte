package de.stefan.lang.designsystem.platformspecific
import de.stefan.lang.designsystem.font.TextStyles
import de.stefan.lang.designsystem.font.TextStylesProviding

data class PlatformSpecificThemeContent(
    override val textStyles: TextStyles
): TextStylesProviding {
}