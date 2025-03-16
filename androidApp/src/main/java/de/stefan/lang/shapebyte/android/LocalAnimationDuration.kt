package de.stefan.lang.shapebyte.android

import de.stefan.lang.shapebyte.android.designsystem.ui.AnimationDuration

object LocalAnimationDuration {
    val current: AnimationDuration by lazy {
        AnimationDuration(
            short = 350,
            long = 700,
        )
    }
}