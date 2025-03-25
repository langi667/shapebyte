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

    fun textStylesCollectionFrom(
        platformSpecific: PlatformSpecificThemeContent,
        fallback: TextStylesProviding
    ): TextStylesCollection? {
         val textStyles: TextStylesCollection? = if(platformSpecific.textStyles as? TextStylesCollection != null) {
             platformSpecific.textStyles
        }
        else {
             fallback.textStyles as? TextStylesCollection
        }

        return textStyles
    }

    fun animationDurationFrom(
        platformSpecific: PlatformSpecificThemeContent,
        fallback: AnimationDurationsProviding
    ): AnimationDurations? {
        val durations: AnimationDurations? = platformSpecific.animationDurations ?: fallback.animationDurations
        return durations
    }
}