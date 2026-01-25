package de.stefan.lang.designsystem.api.core

import de.stefan.lang.designsystem.api.ColorDescriptor

// TODO: test
public val ColorDescriptor.colorHexValue: Long
    get() = when (val color = this) {
        is ColorDescriptor.Hex -> color.value
        else -> throw IllegalArgumentException("ColorDescriptor is not a Hex")
    }
