package de.stefan.lang.shapebyte.features.workout.data.contract

public sealed interface WorkoutType {
    public sealed interface Timed : WorkoutType {
        public data class Interval(
            public val highDurationSec: Int,
            public val lowDurationSec: Int,
            public val rounds: Int,
        ) : Timed {

            public val secondsTotal: Int = (highDurationSec + lowDurationSec) * rounds
        }
    }
}
