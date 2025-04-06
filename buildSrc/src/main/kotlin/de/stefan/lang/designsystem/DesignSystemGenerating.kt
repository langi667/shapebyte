package de.stefan.lang.designsystem
import de.stefan.lang.designsystem.font.TextStylesCollection
import de.stefan.lang.designsystem.font.TextStylesProviding
import de.stefan.lang.designsystem.platformspecific.PlatformSpecificThemeContent
import de.stefan.lang.designsystem.animation.AnimationDurations
import de.stefan.lang.designsystem.animation.AnimationDurationsProviding

import java.io.File
interface DesignSystemGenerating {
    fun generate(
        outputFile: File,
    )
}