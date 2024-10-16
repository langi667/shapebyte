package de.stefan.lang.shapebyte.designsystem.data

data class ColorSchemeDescriptor(
    val defaultColor: ColorDescriptor,
    val darkModeColor: ColorDescriptor,
) {
    constructor(defaultColor: ColorDescriptor) : this(defaultColor, defaultColor)
}
