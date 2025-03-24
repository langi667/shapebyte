package de.stefan.lang.designsystem.shapes

sealed interface Shape {
    data class RoundedCorner(val radius: Int): Shape


}