package de.stefan.lang.shapebyte.designsystem.ui

import de.stefan.lang.shapebyte.designsystem.data.Dimension
import de.stefan.lang.shapebyte.designsystem.data.FontCollection

expect class ThemeProvider {
    val spacing: Dimension
    val fonts: FontCollection
}
