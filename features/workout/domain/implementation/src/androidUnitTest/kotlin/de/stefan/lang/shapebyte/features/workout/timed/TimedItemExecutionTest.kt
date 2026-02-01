package de.stefan.lang.shapebyte.features.workout.timed

import BaseTest
import app.cash.turbine.test
import de.stefan.lang.coreutils.contract.progress.Progress
import de.stefan.lang.shapebyte.features.workout.data.contract.exercise.Exercise
import de.stefan.lang.shapebyte.features.workout.data.contract.item.Item
import de.stefan.lang.shapebyte.features.workout.data.contract.item.ItemSet
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.ItemExecutionState
import de.stefan.lang.shapebyte.features.workout.domain.implementation.timed.TimedItemExecution
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.TimedItemExecutionData
import de.stefan.lang.utils.logging.LoggingModule
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

internal class TimedItemExecutionTest : BaseTest() {
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
            assertEquals(ItemExecutionState.Started(item), awaitItem())

            for (i in 0 until seconds) {
                val setDuration = 1.seconds
                val currSet = sut.sets[i]
                var totalTimePassed = setDuration * i
                var totalTimeRemaining = seconds.seconds - totalTimePassed

                val setStartedState = ItemExecutionState.SetStarted(
                    item = item,
                    set = currSet,
                    progress = Progress.Companion.ZERO,
                    totalProgress = Progress.Companion.with(i, seconds),
                    setData = TimedItemExecutionData(
                        setDuration = setDuration,
                        setTimePassed = 0.seconds,
                        totalTimePassed = totalTimePassed,
                        totalTimeRemaining = totalTimeRemaining,
                        totalDuration = seconds.seconds,
                    ),
                )

                assertEquals(setStartedState, awaitItem())
                val interval = 10

                for (currMS in interval..1000 step interval) {
                    val state = awaitItem()

                    totalTimePassed += interval.milliseconds
                    totalTimeRemaining -= interval.milliseconds

                    val setRunningState = ItemExecutionState.SetRunning(
                        item = item,
                        set = currSet,
                        progress = Progress.Companion.with(currMS, 1000),
                        totalProgress = Progress.Companion.with(totalTimePassed, seconds.seconds),
                        setData = TimedItemExecutionData(
                            setDuration = setDuration,
                            setTimePassed = currMS.milliseconds,
                            totalTimePassed = totalTimePassed,
                            totalTimeRemaining = totalTimeRemaining,
                            totalDuration = seconds.seconds,
                        ),
                    )

                    assertEquals(setRunningState, state)
                }

                val setFinishedState = ItemExecutionState.SetFinished(
                    item = item,
                    set = currSet,
                    progress = Progress.Companion.COMPLETE,
                    totalProgress = Progress.Companion.with(i + 1, seconds),
                    setData = TimedItemExecutionData(
                        setDuration = setDuration,
                        setTimePassed = setDuration,
                        totalTimePassed = totalTimePassed,
                        totalTimeRemaining = totalTimeRemaining,
                        totalDuration = seconds.seconds,
                    ),
                )

                assertEquals(setFinishedState, awaitItem())
            }

            assertEquals(ItemExecutionState.Finished(item, true), awaitItem())
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
            assertEquals(ItemExecutionState.Finished(item, true), awaitItem())
            expectNoEvents()
        }
    }

    @Test
    fun `countdown should emit correct states`() = test {
        val seconds = 10
        val sut = TimedItemExecution.Companion.countdown(seconds.toUInt(), LoggingModule.logger())
        val item = sut.item
        assertTrue(sut.start(this))
        assertTrue(sut.isRunning)

        sut.state.test {
            assertEquals(ItemExecutionState.Started(item), awaitItem())

            for (i in 0 until seconds) {
                val setDuration = 1.seconds
                val currSet = sut.sets[i]
                var totalTimePassed = setDuration * i
                var totalTimeRemaining = seconds.seconds - totalTimePassed

                val setStartedState = ItemExecutionState.SetStarted(
                    item,
                    currSet,
                    Progress.Companion.ZERO,
                    Progress.Companion.with(i, seconds),
                    TimedItemExecutionData(
                        setDuration = setDuration,
                        setTimePassed = 0.seconds,
                        totalTimePassed = totalTimePassed,
                        totalTimeRemaining = totalTimeRemaining,
                        totalDuration = seconds.seconds,
                    ),
                )

                assertEquals(setStartedState, awaitItem())
                val interval = 10

                for (currMS in 0 until 1000 step interval) {
                    val state = awaitItem()

                    totalTimePassed += interval.milliseconds
                    totalTimeRemaining -= interval.milliseconds
                    assertIs<ItemExecutionState.SetRunning<*>>(state)
                }

                val setFinishedState = ItemExecutionState.SetFinished(
                    item = item,
                    set = currSet,
                    progress = Progress.Companion.COMPLETE,
                    totalProgress = Progress.Companion.with(i + 1, seconds),
                    setData = TimedItemExecutionData(
                        setDuration = setDuration,
                        setTimePassed = setDuration,
                        totalTimePassed = totalTimePassed,
                        totalTimeRemaining = totalTimeRemaining,
                        totalDuration = seconds.seconds,
                    ),
                )

                assertEquals(setFinishedState, awaitItem())
            }

            assertEquals(ItemExecutionState.Finished(item, true), awaitItem())
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
            // prev state
            awaitItem()

            for (i in pauseAfter until seconds) {
                // Continues with a finished state because the set was paused and now resumed
                // After that started and finished will be posted as usual
                if (i > pauseAfter) {
                    val setStartedState = awaitItem()
                    assertIs<ItemExecutionState.SetStarted<*>>(setStartedState)
                }

                val setFinishedState = awaitItem()
                assertIs<ItemExecutionState.SetFinished<*>>(setFinishedState)
            }

            assertEquals(ItemExecutionState.Finished(item, true), awaitItem())
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

    @Test
    fun `stop should emit correct values`() = test {
        val item = Exercise("Test")
        val sut = createSUT(item, 3)

        sut.start(this)

        sut.state.test {
            var finish = false
            do {
                when (val currItemState = awaitItem()) {
                    is ItemExecutionState.SetRunning<*> -> {
                        val data = currItemState.setData as TimedItemExecutionData

                        if (data.totalTimePassed == 1.seconds) {
                            assertTrue(sut.stop())
                        }
                    }

                    is ItemExecutionState.Finished -> {
                        finish = true
                        assertEquals(ItemExecutionState.Finished(item, false), sut.state.value)
                        expectNoEvents()
                    }

                    else -> continue
                }

            } while (!finish)
            assertEquals(ItemExecutionState.Finished(item, false), sut.state.value)
        }
    }

    @Test
    fun `stop returns false if not started`() = test {
        val item = Exercise("Test")
        val sut = createSUT(item, 3)

        assertFalse(sut.stop())
    }

    @Test
    fun `stop returns false if finished`() = test {
        val item = Exercise("Test")
        val sut = createSUT(item, 4)

        sut.start(this)

        // wait until finished
        sut.state.filterIsInstance<ItemExecutionState.Finished>().test {
            awaitItem()
            assertFalse(sut.stop())
        }

        assertFalse(sut.stop())
    }

    private fun createSUT(item: Item, seconds: Int): TimedItemExecution {
        val items = List(seconds) { ItemSet.Timed.Seconds(1) }
        return createSUT(item, items)
    }

    private fun createSUT(item: Item, sets: List<ItemSet.Timed.Seconds>) =
        WorkoutDomainModule.createTimedItemExecution(item, sets)
}