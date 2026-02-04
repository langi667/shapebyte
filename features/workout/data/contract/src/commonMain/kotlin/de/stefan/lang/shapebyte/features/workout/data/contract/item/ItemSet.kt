package de.stefan.lang.shapebyte.features.workout.data.contract.item

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

/**
Representing a single performance of either an exercise (push up, squat) or break or countdown
 */

public const val SECOND_IN_MILLISECONDS: Int = 1000

public sealed interface ItemSet {
    public sealed interface Timed : ItemSet {
        public val milliSecsRaw: Int

        public val seconds: Duration get() = (milliSecsRaw / SECOND_IN_MILLISECONDS).seconds
        public val milliseconds: Duration get() = milliSecsRaw.milliseconds

        public data class Seconds(public val durationSeconds: Int) : Timed {
            override val milliSecsRaw: Int = durationSeconds * SECOND_IN_MILLISECONDS
        }

        public data class Milliseconds(override val milliSecsRaw: Int) : Timed
    }

    public data class Repetition(public val repetitions: UInt? = null) : ItemSet
}
