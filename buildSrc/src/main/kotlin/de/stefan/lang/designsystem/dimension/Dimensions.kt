package de.stefan.lang.designsystem.dimension

import de.stefan.lang.designsystem.core.PropertyReader

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
    fun all(): HashMap<String, Int> = PropertyReader.read<Int, Dimensions>(this)
}
