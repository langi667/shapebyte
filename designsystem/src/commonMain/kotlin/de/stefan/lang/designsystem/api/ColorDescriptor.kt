package de.stefan.lang.designsystem.api

public sealed interface ColorDescriptor {

    public sealed interface Themed : ColorDescriptor

    public data object Primary : Themed
    public data object Secondary : Themed
    public data object Background : Themed
    public data object InversePrimary : Themed

    public data class NamedAsset(val value: String) : ColorDescriptor

    // always use this for android color values!
    public data class Hex(val value: Long) : ColorDescriptor
}
