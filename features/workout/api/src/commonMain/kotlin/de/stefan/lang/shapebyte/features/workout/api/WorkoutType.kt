package de.stefan.lang.shapebyte.features.workout.api

sealed interface WorkoutType {
    sealed interface Timed : WorkoutType {
        data class Interval(
            val highDurationSec: Int,
            val lowDurationSec: Int,
            val rounds: Int,
        ) : Timed {

            val secondsTotal: Int = (highDurationSec + lowDurationSec) * rounds
        }
    }
}
