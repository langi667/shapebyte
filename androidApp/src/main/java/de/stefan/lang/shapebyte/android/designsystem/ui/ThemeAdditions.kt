package de.stefan.lang.shapebyte.android.designsystem.ui

import de.stefan.lang.coreutils.logging.Logging
import de.stefan.lang.designsystem.Dimensions
import de.stefan.lang.designsystem.Spacings
import de.stefan.lang.shapebyte.SharedModule

data object ThemeAdditions {
    val logger: Logging = SharedModule.logger()
    val animationDurations: AnimationDuration = AnimationDuration(short = 350, long = 700)

    val dimensions = Dimensions(
        xTiny = 16,
        tiny = 24,
        small = 36,
        medium = 72,
        large = 128,
        xLarge = 192,
        xxLarge = 256,
        xxxLarge = 320,
    )

    val spacings: Spacings =
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
