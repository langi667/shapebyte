package de.stefan.lang.shapebyte.features.workout.workoutData

import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.shapebyte.features.workout.api.item.ItemSet
import kotlin.test.Test
import kotlin.test.assertEquals

class ItemSetTest : CoreTest() {
    @Test
    fun `Seconds should return correct values`() {
        val sut = ItemSet.Timed.Seconds(5)
        assertEquals(5, sut.seconds.inWholeSeconds)
        assertEquals(5000, sut.milliseconds.inWholeMilliseconds)
    }

    @Test
    fun `Milliseconds should return correct seconds values`() {
        val sut = ItemSet.Timed.Milliseconds(5000)
        assertEquals(5, sut.seconds.inWholeSeconds)
        assertEquals(5000, sut.milliseconds.inWholeMilliseconds)
    }
}
