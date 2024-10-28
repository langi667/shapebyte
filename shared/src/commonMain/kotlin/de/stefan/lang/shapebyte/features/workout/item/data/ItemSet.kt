package de.stefan.lang.shapebyte.features.workout.item.data

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
Representing a single performance of either an exercise (push up, squat) or break or countdown
 */

sealed interface ItemSet {
    // TODO: make interface Timed,  have Timed.Seconds and Timed.Milliseconds

    data class Timed(val durationSeconds: Int) : ItemSet {
        val seconds: Duration
            get() = durationSeconds.seconds
    }

    data class Repetition(val repetitions: UInt? = null) : ItemSet
}

// TODO: test
fun List<ItemSet.Timed>.sumDurations(): Duration {
    val retVal: Duration = if (this.isEmpty()) {
        Duration.ZERO
    } else {
        this.map { it.seconds }
            .sumOf { it.inWholeSeconds }.seconds
    }

    return retVal
}

// TODO: test
fun List<ItemSet.Timed>.sumDurationTo(index: Int): Duration {
    val retVal = if (this.isEmpty()) {
        return Duration.ZERO
    } else {
        val list = if (index == 0) {
            listOf(this[0])
        } else {
            this.subList(0, index)
        }

        list.sumDurations()
    }

    return retVal
}
