package de.stefan.lang.shapebyte.designsystem.ui

import de.stefan.lang.shapebyte.designsystem.data.ColorCollection
import de.stefan.lang.shapebyte.designsystem.data.Dimension
import de.stefan.lang.shapebyte.designsystem.data.FontCollection

expect object ThemeProvider {
    val spacing: Dimension
    val fonts: FontCollection
    val colors: ColorCollection
    val dimensions: Dimension
}
