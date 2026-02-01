package de.stefan.lang.shapebyte.features.workout.item

import de.stefan.lang.shapebyte.features.workout.BaseTest
import de.stefan.lang.shapebyte.features.workout.data.contract.WorkoutType
import de.stefan.lang.shapebyte.features.workout.data.contract.item.ItemSet
import de.stefan.lang.shapebyte.features.workout.domain.implementation.item.ItemsExecutionBuilder
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ItemsExecutionBuilderTest : BaseTest() {
    @Test
    fun `buildWithTimedInterval should return correct items execution for timed interval`() {
        val sut = ItemsExecutionBuilder()
        val rounds = 5
        val highDurationSec = 15
        val lowDurationSec = 10

        val result = sut.buildWith(
            WorkoutType.Timed.Interval(
                highDurationSec = highDurationSec,
                lowDurationSec = lowDurationSec,
                rounds = rounds,
            ),
        )

        assertEquals(rounds * 2, result.items.count())
        result.items.forEachIndexed { index, itemExecuting ->
            if (index.mod(2) == 0) { // high intense exercise
                // each set contains 1 second of time
                assertEquals(highDurationSec, itemExecuting.sets.count())
                assertEquals(
                    highDurationSec,
                    itemExecuting.sets.sumOf { (it as ItemSet.Timed.Seconds).durationSeconds },
                )
            } else { // low intense exercise
                // each set contains 1 second of time
                assertEquals(lowDurationSec, itemExecuting.sets.count())
                assertEquals(
                    lowDurationSec,
                    itemExecuting.sets.sumOf { (it as ItemSet.Timed.Seconds).durationSeconds },
                )
            }
        }
    }
}