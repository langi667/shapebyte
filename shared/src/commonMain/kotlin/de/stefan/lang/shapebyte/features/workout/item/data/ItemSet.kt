package de.stefan.lang.shapebyte.features.workout.item.data

import kotlin.time.Duration

/**
Representing a single performance of either an exercise (push up, squat) or break or countdown
 */

sealed class ItemSet(val item: Item) {
    class Timed(val duration: Duration, item: Item = None) : ItemSet(item)
    class Repetition(item: Item, val maxRepetitions: UInt? = null) : ItemSet(item)
}
