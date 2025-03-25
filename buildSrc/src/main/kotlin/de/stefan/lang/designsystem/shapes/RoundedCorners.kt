package de.stefan.lang.designsystem.shapes

import de.stefan.lang.designsystem.core.PropertyReader
import de.stefan.lang.designsystem.font.TextStyle
import org.gradle.kotlin.dsl.provideDelegate
import kotlin.reflect.full.declaredMemberProperties

data class RoundedCorners(
    val small: Shape.RoundedCorner,
    val medium: Shape.RoundedCorner,
    val large: Shape.RoundedCorner,
    val extraLarge: Shape.RoundedCorner,
) {

    val all: HashMap<String, Shape.RoundedCorner> by lazy {
        PropertyReader.read<Shape.RoundedCorner, RoundedCorners>(this)
    }

    val allSorted by lazy {
        all
            .entries
            .sortedWith (
                compareBy<Map.Entry<String, Shape.RoundedCorner>> { it.value.radius }
            )
            .associate { it.key to it.value }
    }
}
