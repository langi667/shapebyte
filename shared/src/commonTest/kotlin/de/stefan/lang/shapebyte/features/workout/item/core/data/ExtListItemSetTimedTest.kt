package de.stefan.lang.shapebyte.features.workout.item.core.data

import de.stefan.lang.shapebyte.utils.BaseTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class ExtListItemSetTimedTest : BaseTest() {

    @Test
    fun `should sum correct number of milliseconds`() {
        listOf(
            ItemSet.Timed.Milliseconds(5),
            ItemSet.Timed.Milliseconds(5),
            ItemSet.Timed.Milliseconds(5),
        ).sumMilliseconds().let {
            assertEquals(15.milliseconds, it)
        }

        listOf(
            ItemSet.Timed.Seconds(5),
            ItemSet.Timed.Seconds(5),
            ItemSet.Timed.Seconds(5),
        ).sumMilliseconds().let {
            assertEquals(15000.milliseconds, it)
            assertEquals(15.seconds, it)
        }

        assertEquals(Duration.ZERO, emptyList<ItemSet.Timed.Milliseconds>().sumMilliseconds())
        assertEquals(Duration.ZERO, emptyList<ItemSet.Timed.Seconds>().sumMilliseconds())
    }

    @Test
    fun `should sum correct number of milliseconds from sublist`() {
        listOf(
            ItemSet.Timed.Milliseconds(5),
            ItemSet.Timed.Milliseconds(5),
            ItemSet.Timed.Milliseconds(5),
        ).sumMillisecondsTo(2).let {
            assertEquals(10.milliseconds, it)
        }

        listOf(
            ItemSet.Timed.Milliseconds(5),
            ItemSet.Timed.Milliseconds(5),
            ItemSet.Timed.Milliseconds(5),
        ).sumMillisecondsTo(0).let {
            assertEquals(Duration.ZERO, it)
        }

        listOf(
            ItemSet.Timed.Milliseconds(5),
            ItemSet.Timed.Milliseconds(5),
            ItemSet.Timed.Milliseconds(5),
        ).sumMillisecondsTo(3).let {
            assertEquals(15.milliseconds, it)
        }

        listOf(
            ItemSet.Timed.Milliseconds(5),
            ItemSet.Timed.Milliseconds(5),
            ItemSet.Timed.Milliseconds(5),
        ).sumMillisecondsTo(3).let {
            assertEquals(15.milliseconds, it)
        }

        listOf(
            ItemSet.Timed.Milliseconds(5),
            ItemSet.Timed.Milliseconds(5),
            ItemSet.Timed.Milliseconds(5),
        ).sumMillisecondsTo(6).let {
            assertEquals(15.milliseconds, it)
        }
    }

    @Test
    fun `should sum correct number of seconds`() {
        listOf(
            ItemSet.Timed.Seconds(5),
            ItemSet.Timed.Seconds(5),
            ItemSet.Timed.Seconds(5),
        ).sumMilliseconds().let {
            assertEquals(15.seconds, it)
        }

        listOf(
            ItemSet.Timed.Milliseconds(5000),
            ItemSet.Timed.Milliseconds(5000),
            ItemSet.Timed.Milliseconds(5000),
        ).sumMilliseconds().let {
            assertEquals(15000.milliseconds, it)
            assertEquals(15.seconds, it)
        }

        assertEquals(Duration.ZERO, emptyList<ItemSet.Timed.Seconds>().sumSeconds())
        assertEquals(Duration.ZERO, emptyList<ItemSet.Timed.Seconds>().sumSeconds())
    }
}
