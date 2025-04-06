package de.stefan.lang.shapebyte.android

import de.stefan.lang.designsystem.Dimensions

object LocalDimension {
    val current: Dimensions by lazy {
        Dimensions(
            xTiny = 16,
            tiny = 24,
            small = 36,
            medium = 72,
            large = 128,
            xLarge = 192,
            xxLarge = 256,
            xxxLarge = 320,
        )
    }
}
