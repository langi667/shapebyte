package de.stefan.lang.shapebyte.features.workout.workoutData.exercise

import de.stefan.lang.shapebyte.features.workout.workoutData.item.ItemSet
import de.stefan.lang.shapebyte.features.workout.workoutData.item.SECOND_IN_MILLISECONDS
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

fun List<ItemSet.Timed>.sumSeconds(): Duration =
    ((this.sumMilliSecsRaw()) / SECOND_IN_MILLISECONDS).seconds

fun List<ItemSet.Timed>.sumSecondsTo(index: Int): Duration =
    ((this.sumMilliSecsRawTo(index)) / SECOND_IN_MILLISECONDS).seconds

fun List<ItemSet.Timed>.sumMilliseconds(): Duration = this.sumMilliSecsRaw().milliseconds
fun List<ItemSet.Timed>.sumMillisecondsTo(index: Int): Duration =
    this.sumMilliSecsRawTo(index).milliseconds

private fun List<ItemSet.Timed>.sumMilliSecsRaw(): Int {
    val retVal: Int = this.sumOf { it.milliSecsRaw }
    return retVal
}

private fun List<ItemSet.Timed>.sumMilliSecsRawTo(length: Int): Int {
    val retVal = if (this.isEmpty()) {
        return 0
    } else {
        val list = if (length == 0) {
            return 0
        } else {
            if (length > this.size) {
                return this.sumMilliSecsRaw()
            }

            this.subList(0, length)
        }

        list.sumMilliSecsRaw()
    }

    return retVal
}
