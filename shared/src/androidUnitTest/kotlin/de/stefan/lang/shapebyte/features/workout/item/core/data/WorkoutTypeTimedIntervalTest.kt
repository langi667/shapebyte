package de.stefan.lang.shapebyte.features.workout.item.core.data

import de.stefan.lang.shapebyte.features.BaseWorkoutFeatureTest
import de.stefan.lang.shapebyte.features.workout.core.data.WorkoutType
import kotlin.test.Test
import kotlin.test.assertEquals

class WorkoutTypeTimedIntervalTest : BaseWorkoutFeatureTest() {
    @Test
    fun `test secondsTotal`() {
        var sut = WorkoutType.Timed.Interval(20, 10, 10)
        assertEquals(300, sut.secondsTotal)

        sut = WorkoutType.Timed.Interval(20, 10, 0)
        assertEquals(0, sut.secondsTotal)

        sut = WorkoutType.Timed.Interval(0, 10, 5)
        assertEquals(50, sut.secondsTotal)

        sut = WorkoutType.Timed.Interval(10, 0, 5)
        assertEquals(50, sut.secondsTotal)
    }
}
