package de.stefan.lang.shapebyte.designsystem.data

sealed interface ColorDescriptor {
    data class NamedAsset(val value: String) : ColorDescriptor

    // always use this for android color values!
    data class Hex(val value: Long) : ColorDescriptor
}
