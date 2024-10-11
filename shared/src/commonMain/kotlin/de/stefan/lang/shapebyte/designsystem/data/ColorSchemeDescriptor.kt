package de.stefan.lang.shapebyte.designsystem.data

data class ColorSchemeDescriptor(
    val defaultColor: ColorDescriptor,
    val darkModeColor: ColorDescriptor,
) {
    // TODO: test
    constructor(defaultColor: ColorDescriptor) : this(defaultColor, defaultColor)
}
