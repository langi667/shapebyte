package de.stefan.lang.shapebyte.features.workout.item.domain.timed

import app.cash.turbine.test
import de.stefan.lang.shapebyte.features.workout.di.WorkoutModule
import de.stefan.lang.shapebyte.features.workout.item.data.Exercise
import de.stefan.lang.shapebyte.features.workout.item.data.Item
import de.stefan.lang.shapebyte.features.workout.item.data.ItemSet
import de.stefan.lang.shapebyte.features.workout.item.domain.ItemExecutionState
import de.stefan.lang.shapebyte.utils.BaseCoroutineTest
import de.stefan.lang.shapebyte.utils.Progress
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

class TimedItemExecutionTest : BaseCoroutineTest() {

    @Test
    fun `initial state`() {
        val seconds = 5
        val item = Exercise("Test")
        val sut = createSUT(item, seconds)

        assertFalse(sut.isRunning)
        assertEquals(item, sut.item)

        assertEquals(5, sut.sets.count())
        assertEquals(sut.state.value, ItemExecutionState.Idle)
    }

    @Test
    fun `start should emit correct values`() = test {
        val seconds = 5
        val item = Exercise("Test")
        val sut = createSUT(item, seconds)

        assertTrue(sut.start(this))
        assertTrue(sut.isRunning)

        sut.state.test {
            val state = awaitItem()
            assertEquals(ItemExecutionState.Started(item), state)

            for (i in 0 until seconds) {
                var state = awaitItem()
                val setDuration = 1.seconds
                val currSet = sut.sets[i]

                var secsPassed = setDuration * i
                var secsRemaining = seconds.seconds - secsPassed

                val started = ItemExecutionState.SetStarted(
                    item,
                    currSet,
                    Progress.ZERO,
                    Progress.with(i, seconds),
                    TimedItemExecutionData(setDuration, secsPassed, secsRemaining, seconds.seconds),
                )

                assertEquals(started, state)
                state = awaitItem()

                secsPassed += 1.seconds
                secsRemaining = seconds.seconds - secsPassed

                val finished = ItemExecutionState.SetFinished(
                    item = item,
                    set = currSet,
                    setProgress = Progress.COMPLETE,
                    totalProgress = Progress.with(i + 1, seconds),
                    setData = TimedItemExecutionData(setDuration, secsPassed, secsRemaining, seconds.seconds),
                )

                assertEquals(finished, state)
            }

            assertEquals(ItemExecutionState.Finished(item), awaitItem())
            assertFalse(sut.isRunning)
            expectNoEvents()
        }
    }

    @Test
    fun `start should not cause running twice`() = test {
        val seconds = 5
        val item = Exercise("Test")
        val sut = createSUT(item, seconds)

        assertTrue(sut.start(this))
        assertTrue(sut.isRunning)
        assertFalse(sut.start(this))
        assertTrue(sut.isRunning)
    }

    @Test
    fun `start with zero seconds should switch from started to finish`() = test {
        val seconds = 0
        val item = Exercise("Test")
        val sut = createSUT(item, seconds)

        assertTrue(sut.start(this))
        assertTrue(sut.isRunning)

        sut.state.test {
            assertEquals(ItemExecutionState.Started(item), awaitItem())
            assertEquals(ItemExecutionState.Finished(item), awaitItem())
            expectNoEvents()
        }
    }

    @Test
    fun `countdown should emit correct states`() = test {
        val seconds = 10
        val sut = TimedItemExecution.countdown(seconds.toUInt())
        val item = sut.item
        assertTrue(sut.start(this))
        assertTrue(sut.isRunning)

        sut.state.test {
            val state = awaitItem()
            assertEquals(ItemExecutionState.Started(item), state)

            for (i in 0 until seconds) {
                var state = awaitItem()
                val setDuration = 1.seconds
                val currSet = sut.sets[i]

                var secsPassed = setDuration * i
                var secsRemaining = seconds.seconds - secsPassed

                val started = ItemExecutionState.SetStarted(
                    item,
                    currSet,
                    Progress.ZERO,
                    Progress.with(i, seconds),
                    TimedItemExecutionData(setDuration, secsPassed, secsRemaining, seconds.seconds),
                )

                assertEquals(started, state)
                state = awaitItem()

                secsPassed += 1.seconds
                secsRemaining = seconds.seconds - secsPassed

                val finished = ItemExecutionState.SetFinished(
                    item = item,
                    set = currSet,
                    setProgress = Progress.COMPLETE,
                    totalProgress = Progress.with(i + 1, seconds),
                    setData = TimedItemExecutionData(
                        setDuration = setDuration,
                        timePassed = secsPassed,
                        timeRemaining = secsRemaining,
                        totalDuration = seconds.seconds,
                    ),
                )

                assertEquals(finished, state)
            }

            assertEquals(ItemExecutionState.Finished(item), awaitItem())
            assertFalse(sut.isRunning)
            expectNoEvents()
        }
    }

    private fun createSUT(item: Item, seconds: Int): TimedItemExecution {
        val items = mutableListOf<ItemSet.Timed>()
        repeat(seconds) {
            items.add(ItemSet.Timed(1.seconds))
        }

        return createSUT(item, items)
    }

    private fun createSUT(item: Item, sets: List<ItemSet.Timed>) =
        WorkoutModule.createTimedItemExecution(item, sets)
}
