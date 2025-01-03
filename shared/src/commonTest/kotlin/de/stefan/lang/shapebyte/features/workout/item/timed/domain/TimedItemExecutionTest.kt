package de.stefan.lang.shapebyte.features.workout.item.timed.domain

import app.cash.turbine.test
import de.stefan.lang.shapebyte.features.workout.di.WorkoutModule
import de.stefan.lang.shapebyte.features.workout.item.core.data.Exercise
import de.stefan.lang.shapebyte.features.workout.item.core.data.Item
import de.stefan.lang.shapebyte.features.workout.item.core.data.ItemSet
import de.stefan.lang.shapebyte.features.workout.item.core.domain.ItemExecutionState
import de.stefan.lang.shapebyte.utils.BaseCoroutineTest
import de.stefan.lang.shapebyte.utils.Progress
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

// TODO: move to Android unit test
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
                var currState = awaitItem()
                val setDuration = 1.seconds
                val currSet = sut.sets[i]

                var secsPassed = setDuration * i
                var secsRemaining = seconds.seconds - secsPassed

                val started = ItemExecutionState.SetStarted(
                    item,
                    currSet,
                    Progress.ZERO,
                    Progress.with(i, seconds),
                    TimedItemExecutionData(
                        setDuration = setDuration,
                        setTimePassed = 0.seconds,
                        totalTimePassed = secsPassed,
                        totalTimeRemaining = secsRemaining,
                        totalDuration = seconds.seconds,
                    ),
                )

                assertEquals(started, currState)
                currState = awaitItem()

                secsPassed += 1.seconds
                secsRemaining = seconds.seconds - secsPassed

                val finished = ItemExecutionState.SetFinished(
                    item = item,
                    set = currSet,
                    progress = Progress.COMPLETE,
                    totalProgress = Progress.with(i + 1, seconds),
                    setData = TimedItemExecutionData(
                        setDuration = setDuration,
                        setTimePassed = setDuration,
                        totalTimePassed = secsPassed,
                        totalTimeRemaining = secsRemaining,
                        totalDuration = seconds.seconds,
                    ),
                )

                assertEquals(finished, currState)
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
                var currState = awaitItem()
                val setDuration = 1.seconds
                val currSet = sut.sets[i]

                var secsPassed = setDuration * i
                var secsRemaining = seconds.seconds - secsPassed

                val started = ItemExecutionState.SetStarted(
                    item,
                    currSet,
                    Progress.ZERO,
                    Progress.with(i, seconds),
                    TimedItemExecutionData(
                        setDuration = setDuration,
                        setTimePassed = 0.seconds,
                        totalTimePassed = secsPassed,
                        totalTimeRemaining = secsRemaining,
                        totalDuration = seconds.seconds,
                    ),
                )

                assertEquals(started, currState)
                currState = awaitItem()

                secsPassed += 1.seconds
                secsRemaining = seconds.seconds - secsPassed

                val finished = ItemExecutionState.SetFinished(
                    item = item,
                    set = currSet,
                    progress = Progress.COMPLETE,
                    totalProgress = Progress.with(i + 1, seconds),
                    setData = TimedItemExecutionData(
                        setDuration = setDuration,
                        setTimePassed = setDuration,
                        totalTimePassed = secsPassed,
                        totalTimeRemaining = secsRemaining,
                        totalDuration = seconds.seconds,
                    ),
                )

                assertEquals(finished, currState)
            }

            assertEquals(ItemExecutionState.Finished(item), awaitItem())
            assertFalse(sut.isRunning)
            expectNoEvents()
        }
    }

    @Test
    fun `test pause and restart`() = test {
        val seconds = 5
        val pauseAfter = 3

        val item = Exercise("Test")
        val sut = createSUT(item, seconds)

        assertTrue(sut.start(this))
        assertTrue(sut.isRunning)

        sut.state.filter {
            it !is ItemExecutionState.SetRunning<*>
        }.test {
            val state = awaitItem()
            assertEquals(ItemExecutionState.Started(item), state)

            for (i in 0 until pauseAfter) {
                val setStartedState = awaitItem()
                assertIs<ItemExecutionState.SetStarted<*>>(setStartedState)

                val setFinishedState = awaitItem()
                assertIs<ItemExecutionState.SetFinished<*>>(setFinishedState)
            }

            sut.pause()
            awaitItem()
            val pauseItem = awaitItem()

            assertEquals(ItemExecutionState.Paused(item), pauseItem)
            expectNoEvents()
        }

        assertTrue(sut.start(this))

        sut.state.filter {
            it !is ItemExecutionState.SetRunning<*>
        }.test {
            awaitItem()
            for (i in pauseAfter until seconds) {
                val setStartedState = awaitItem()
                assertIs<ItemExecutionState.SetStarted<*>>(setStartedState)

                val setFinishedState = awaitItem()
                assertIs<ItemExecutionState.SetFinished<*>>(setFinishedState)
            }

            assertEquals(ItemExecutionState.Finished(item), awaitItem())
            assertFalse(sut.isRunning)
            expectNoEvents()
        }
    }

    @Test
    fun `pause should return false if not running`() = test {
        val item = Exercise("Test")
        val sut = createSUT(item, 1)

        assertFalse(sut.pause())
        sut.start(this)

        // wait until finished
        sut.state.filterIsInstance<ItemExecutionState.Finished>().test {
            awaitItem()
            assertFalse(sut.pause())
        }
    }

    private fun createSUT(item: Item, seconds: Int): TimedItemExecution {
        val items = List(seconds) { ItemSet.Timed.Seconds(1) }
        return createSUT(item, items)
    }

    private fun createSUT(item: Item, sets: List<ItemSet.Timed.Seconds>) =
        WorkoutModule.createTimedItemExecution(item, sets)
}
