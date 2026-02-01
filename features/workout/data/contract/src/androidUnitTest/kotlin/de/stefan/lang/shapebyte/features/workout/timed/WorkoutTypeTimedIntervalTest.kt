package de.stefan.lang.shapebyte.features.workout.timed

import de.stefan.lang.shapebyte.features.workout.data.contract.WorkoutType
import kotlin.test.Test
import kotlin.test.assertEquals

class WorkoutTypeTimedIntervalTest {
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