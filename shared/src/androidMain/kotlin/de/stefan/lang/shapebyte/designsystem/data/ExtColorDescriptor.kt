package de.stefan.lang.shapebyte.designsystem.data

// TODO: test
val ColorDescriptor.colorHexValue: Long
    get() = when (val color = this) {
        is ColorDescriptor.Hex -> color.value
        is ColorDescriptor.NamedAsset -> throw IllegalArgumentException("ColorDescriptor is not a Hex")
    }
