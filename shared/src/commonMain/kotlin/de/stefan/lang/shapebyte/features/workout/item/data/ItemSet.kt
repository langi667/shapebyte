package de.stefan.lang.shapebyte.features.workout.item.data

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

/**
Representing a single performance of either an exercise (push up, squat) or break or countdown
 */

internal const val SECOND_IN_MILLISECONDS = 1000

sealed interface ItemSet {
    sealed interface Timed : ItemSet {
        val milliSecsRaw: Int

        val seconds: Duration get() = (milliSecsRaw / SECOND_IN_MILLISECONDS).seconds
        val milliseconds: Duration get() = milliSecsRaw.milliseconds

        data class Seconds(val durationSeconds: Int) : Timed {
            override val milliSecsRaw: Int = durationSeconds * SECOND_IN_MILLISECONDS
        }

        data class Milliseconds(override val milliSecsRaw: Int) : Timed
    }

    data class Repetition(val repetitions: UInt? = null) : ItemSet
}
