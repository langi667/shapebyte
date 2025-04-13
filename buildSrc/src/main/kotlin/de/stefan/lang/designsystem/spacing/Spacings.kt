package de.stefan.lang.designsystem.spacing

import de.stefan.lang.designsystem.animation.AnimationDurations
import de.stefan.lang.designsystem.core.PropertyReader
import de.stefan.lang.designsystem.shapes.Shape
import org.gradle.kotlin.dsl.provideDelegate

data class Spacings(
    val xTiny: Int,
    val tiny: Int,
    val small: Int,
    val medium: Int,
    val large: Int,
    val xLarge: Int,
    val xxLarge: Int,
    val xxxLarge: Int,
) {
    val all: Map<String, Int> by lazy {
        PropertyReader.read<Int, Spacings>(this)
    }

    val allSorted by lazy {
        all
            .entries
            .sortedWith (
                compareBy{ it.value }
            )
            .associate { it.key to it.value }
    }
}
