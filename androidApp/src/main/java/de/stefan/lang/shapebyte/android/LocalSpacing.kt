package de.stefan.lang.shapebyte.android

import de.stefan.lang.designsystem.Spacing

object LocalSpacing {
    val current: Spacing by lazy {
        Spacing(
            xTiny = 4,
            tiny = 8,
            small = 16,
            medium = 32,
            large = 48,
            xLarge = 64,
            xxLarge = 84,
            xxxLarge = 128,
        )
    }
}