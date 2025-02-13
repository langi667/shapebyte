package de.stefan.lang.designsystem

sealed interface FontDescriptor {
    data class System(
        override val size: Int,
        override val weight: FontWeight,
    ) : FontDescriptor

    // TODO: add custom font support

    val size: Int
    val weight: FontWeight
}
