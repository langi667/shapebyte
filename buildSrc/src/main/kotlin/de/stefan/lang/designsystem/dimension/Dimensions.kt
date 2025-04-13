package de.stefan.lang.designsystem.dimension

import de.stefan.lang.designsystem.core.PropertyReader
import de.stefan.lang.designsystem.shapes.Shape
import org.gradle.kotlin.dsl.provideDelegate

data class Dimensions(
    val xTiny: Int,
    val tiny: Int,
    val small: Int,
    val medium: Int,
    val large: Int,
    val xLarge: Int,
    val xxLarge: Int,
    val xxxLarge: Int,
) {
    val all by lazy  { PropertyReader.read<Int, Dimensions>(this) }

    val allSorted by lazy {
        all
            .entries
            .sortedWith (
                compareBy<Map.Entry<String, Int>> { it.value }
            )
            .associate { it.key to it.value }
    }
}
