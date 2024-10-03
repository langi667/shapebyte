package de.stefan.lang.shapebyte.features.workout.data

import kotlin.time.Duration

/**
Representing a single performance of either an exercise (push up, squat) or break or countdown
 */

sealed class ItemSet(val item: Item) {
    class Timed(val duration: Duration, item: Item) : ItemSet(item)
}
