package de.stefan.lang.shapebyte.utils.designsystem.data

// TODO: test
val ColorDescriptor.colorHexValue: Long
    get() = when (val color = this) {
        is ColorDescriptor.Hex -> color.value
        else -> throw IllegalArgumentException("ColorDescriptor is not a Hex")
    }
