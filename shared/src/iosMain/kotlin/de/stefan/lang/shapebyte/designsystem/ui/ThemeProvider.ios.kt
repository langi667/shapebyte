package de.stefan.lang.shapebyte.designsystem.ui

import de.stefan.lang.shapebyte.designsystem.data.ColorCollection
import de.stefan.lang.shapebyte.designsystem.data.ColorDescriptor
import de.stefan.lang.shapebyte.designsystem.data.ColorSchemeDescriptor
import de.stefan.lang.shapebyte.designsystem.data.Dimension
import de.stefan.lang.shapebyte.designsystem.data.FontCollection
import de.stefan.lang.shapebyte.designsystem.data.FontDescriptor
import de.stefan.lang.shapebyte.designsystem.data.FontWeight

actual object ThemeProvider {
    actual val spacing: Dimension = Dimension(
        xxs = 4,
        xs = 8,
        small = 16,
        medium = 32,
        large = 48,
        xLarge = 64,
        xxLarge = 84,
        xxxLarge = 128,
    )

    actual val fonts = FontCollection(
        title = FontDescriptor.System (
            weight = FontWeight.Black,
            size = 64
        ),
        subtitle = FontDescriptor.System(
            weight = FontWeight.Light,
            size = 32
        ),
        h1 = FontDescriptor.System(
            weight = FontWeight.Bold,
            size = 28
        ),
        h2 = FontDescriptor.System(
            weight = FontWeight.Bold,
            size = 24
        ),
        h3 = FontDescriptor.System(
            weight = FontWeight.Bold,
            size = 21
        ),
        h4 = FontDescriptor.System(
            weight = FontWeight.Bold,
            size = 19
        ),
        body = FontDescriptor.System(
            weight = FontWeight.Regular,
            size = 17
        ),
        footnote = FontDescriptor.System(
            weight = FontWeight.Regular,
            size = 14
        )
    )

    actual val colors = ColorCollection(
        primary = ColorSchemeDescriptor(
            defaultColor = ColorDescriptor.NamedAsset("PrimaryColor"),
        ),
        secondary = ColorSchemeDescriptor(
            defaultColor = ColorDescriptor.NamedAsset("SecondaryColor"),
        ),
        background = ColorSchemeDescriptor(
            defaultColor = ColorDescriptor.NamedAsset("BackgroundColor"),
        ),
    )

    actual val dimensions: Dimension = Dimension(
        xxs = 16,
        xs = 24,
        small = 36,
        medium = 72,
        large = 128,
        xLarge = 192,
        xxLarge = 256,
        xxxLarge = 320,
    )
}