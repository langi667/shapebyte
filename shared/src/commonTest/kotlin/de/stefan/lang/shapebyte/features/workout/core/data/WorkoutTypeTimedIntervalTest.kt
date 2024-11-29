package de.stefan.lang.shapebyte.features.workout.core.data

import de.stefan.lang.shapebyte.utils.BaseTest
import kotlin.test.Test
import kotlin.test.assertEquals

class WorkoutTypeTimedIntervalTest : BaseTest() {
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
