package de.stefan.lang.shapebyte.android

import de.stefan.lang.designsystem.Spacings

object LocalSpacing {
    val current: Spacings by lazy {
        Spacings(
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
