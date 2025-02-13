package de.stefan.lang.designsystem

sealed interface ColorDescriptor {

    sealed interface Themed : ColorDescriptor

    data object Primary : Themed
    data object Secondary : Themed
    data object Background : Themed
    data object InversePrimary : Themed

    data class NamedAsset(val value: String) : ColorDescriptor

    // always use this for android color values!
    data class Hex(val value: Long) : ColorDescriptor
}
