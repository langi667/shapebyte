package de.stefan.lang.designsystem.animation

import de.stefan.lang.designsystem.core.PropertyReader
import de.stefan.lang.designsystem.shapes.Shape
import org.gradle.kotlin.dsl.provideDelegate

data class AnimationDurations(
    val short: Double,
    val medium: Double,
    val long: Double,
) {
    val all: Map<String, Double> by lazy {
        PropertyReader.read<Double, AnimationDurations>(this)
    }

    val allSorted by lazy {
        all
            .entries
            .sortedWith (
                compareBy<Map.Entry<String, Double>> { it.value }
            )
            .associate { it.key to it.value }
    }
}