package de.stefan.lang.shapebyte.features.workout.item

import app.cash.turbine.test
import de.stefan.lang.coreutils.CoreUtilsModule
import de.stefan.lang.coreutils.api.progress.Progress
import de.stefan.lang.coreutils.api.logging.Logging
import de.stefan.lang.shapebyte.features.workout.WorkoutFeatureTest
import de.stefan.lang.shapebyte.features.workout.api.exercise.Exercise
import de.stefan.lang.shapebyte.features.workout.api.item.Item
import de.stefan.lang.shapebyte.features.workout.api.item.ItemSet
import de.stefan.lang.shapebyte.features.workout.workoutDomain.WorkoutDomainModule
import de.stefan.lang.shapebyte.features.workout.workoutDomain.item.ItemExecuting
import de.stefan.lang.shapebyte.features.workout.workoutDomain.item.ItemExecutionState
import de.stefan.lang.shapebyte.features.workout.workoutDomain.item.ItemsExecution
import de.stefan.lang.shapebyte.features.workout.workoutDomain.item.ItemsExecutionState
import de.stefan.lang.shapebyte.features.workout.workoutDomain.item.TimedItemExecuting
import de.stefan.lang.shapebyte.features.workout.workoutDomain.item.TimedItemExecutionData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.fail

class ItemsExecutionTest : WorkoutFeatureTest() {
    /**
     * To test that if start is failing, the other items in the ItemsExecution are executed
     */
    class TimedFailing(
        override val item: Item,
        override val sets: List<ItemSet.Timed.Seconds>,
        override val logger: Logging,
    ) : TimedItemExecuting {

        override val state: StateFlow<ItemExecutionState<TimedItemExecutionData>> =
            MutableStateFlow(ItemExecutionState.Idle)

        override fun start(scope: CoroutineScope): Boolean {
            return false
        }

        override fun pause(): Boolean {
            return false
        }

        override fun stop(): Boolean {
            return false
        }
    }

    @Test
    fun `initial state`() = test {
        val sut = createSUT(emptyList())
        assertEquals(ItemsExecutionState.Idle, sut.state.value)
    }

    @Test
    fun `is finished if items are empty`() = test {
        val sut = createSUT(emptyList())
        sut.start(this)

        sut.state.test {
            assertIs<ItemsExecutionState.Started>(awaitItem())
            assertIs<ItemsExecutionState.Finished>(awaitItem())

            expectNoEvents()
        }
    }

    @Test
    fun `emits correct states for timed`() = test {
        val executions = 5
        val duration = 3
        val setCount = executions * duration

        val itemSet = ItemSet.Timed.Seconds(1)
        val sut = createSUT(
            List(executions) {
                WorkoutDomainModule.createTimedItemExecution(
                    item = Exercise("Test $it"),
                    sets = List(duration) { itemSet },
                )
            },
        )

        sut.start(this)
        sut.state.test {
            assertIs<ItemsExecutionState.Started>(awaitItem())
            var setsFinished = 0

            for (currExecution in 0..<executions) {
                repeat((0..<duration).count()) {
                    val interval = 10
                    val setDurationMS = 1000

                    // each set. 15 sets which are 1000 ms long. Every 10 ms an update is posted
                    for (currMS in 0..setDurationMS step interval) {
                        val runningState = awaitItem() as ItemsExecutionState.Running
                        val expectedItem = Exercise("Test $currExecution")
                        val completed = Progress.with(current = setsFinished, total = setCount)
                        val running =
                            Progress((1.0f / setCount.toFloat()) * (currMS.toFloat() / setDurationMS.toFloat()))
                        val totalProgress = completed + running

                        assertEquals(expectedItem, runningState.item)
                        assertEquals(executions, runningState.itemCount)
                        assertEquals(currExecution, runningState.itemIndex)
                        assertEquals(totalProgress, runningState.totalProgress)

                        if (runningState.itemState is ItemExecutionState.SetFinished) {
                            setsFinished += 1
                        }
                    }
                }
            }

            assertIs<ItemsExecutionState.Finished>(awaitItem())
            assertEquals(setCount, setsFinished)
            expectNoEvents()
        }
    }

    @Test
    fun `emits correct states even if items cannot start`() = test {
        val executionsSucceedBefore = 2
        val executionsSucceedAfter = 2
        val duration = 3
        val ignoredItem = Exercise("Fail")

        val items = List(executionsSucceedBefore) {
            WorkoutDomainModule.createTimedItemExecution(
                item = Exercise("Test $it"),
                sets = List(duration) { ItemSet.Timed.Seconds(1) },
            )
        } + listOf(
            TimedFailing(
                item = ignoredItem,
                sets = List(duration) { ItemSet.Timed.Seconds(1) },
                CoreUtilsModule.logger(),
            ),
        ) +
            List(executionsSucceedAfter) {
                WorkoutDomainModule.createTimedItemExecution(
                    item = Exercise("Test ${executionsSucceedBefore + it}"),
                    sets = List(duration) { ItemSet.Timed.Seconds(1) },
                )
            }

        val sut = createSUT(items)
        val expectedItems = mutableSetOf<Item>()

        sut.start(this)
        sut.state
            .filter { it is ItemsExecutionState.Running || it is ItemsExecutionState.Finished }
            .test {
                var runTest = true
                do {
                    val state = awaitItem()

                    if (state is ItemsExecutionState.Running) {
                        expectedItems.add(state.item)
                    } else if (state is ItemsExecutionState.Finished) {
                        expectNoEvents()
                        runTest = false
                    }
                } while (runTest)
            }

        assertEquals(executionsSucceedAfter + executionsSucceedBefore, expectedItems.count())
        assertFalse(expectedItems.contains(ignoredItem))
    }

    @Test
    fun `emits correct states for timed with paused`() = test {
        val executions = 6
        val duration = 4

        val itemSet = ItemSet.Timed.Seconds(1)
        val sut = createSUT(
            List(executions) {
                WorkoutDomainModule.createTimedItemExecution(
                    item = Exercise("Test $it"),
                    sets = List(duration) { itemSet },
                )
            },
        )

        sut.start(this)
        var pausedAtState: ItemsExecutionState.Running? = null

        sut.state.test {
            assertIs<ItemsExecutionState.Started>(awaitItem())
            var awaitItem = true

            do {
                val runningState = awaitItem()
                assertIs<ItemsExecutionState.Running>(runningState)
                if (runningState.totalProgress.value >= 0.5f && runningState.itemState.progress.value == 0.5f) {
                    pausedAtState = runningState

                    sut.pause()
                    awaitItem = false
                }
            } while (awaitItem)

            assertIs<ItemsExecutionState.Paused>(awaitItem())
            expectNoEvents()
        }

        assertNotNull(pausedAtState)

        // resume
        sut.start(this)
        sut.state.test {
            assertIs<ItemsExecutionState.Paused>(awaitItem())
            val firstRunningStateAfterPaused = awaitItem() as ItemsExecutionState.Running

            assertEquals(pausedAtState!!.item, firstRunningStateAfterPaused.item)
            assertEquals(pausedAtState!!.itemIndex, firstRunningStateAfterPaused.itemIndex)
            assertEquals(pausedAtState!!.itemCount, firstRunningStateAfterPaused.itemCount)

            // continue with larger progress
            assertTrue(firstRunningStateAfterPaused.totalProgress.value > pausedAtState!!.totalProgress.value)

            // should finish
            var running = true
            var prevState: ItemsExecutionState.Running = firstRunningStateAfterPaused

            do {
                val currState = awaitItem()
                if (currState is ItemsExecutionState.Finished) {
                    // last running state, should have complete progress
                    assertEquals(prevState.totalProgress, Progress.COMPLETE)
                    running = false
                } else {
                    assertIs<ItemsExecutionState.Running>(currState)
                    prevState = currState
                }
            } while (running)

            expectNoEvents()
        }
    }

    // TODO: Test stop


    @Test
    fun `stop should return false if not launched`() = test {
        val executions = 6
        val duration = 4

        val itemSet = ItemSet.Timed.Seconds(1)
        val sut = createSUT(
            List(executions) {
                WorkoutDomainModule.createTimedItemExecution(
                    item = Exercise("Test $it"),
                    sets = List(duration) { itemSet },
                )
            },
        )
        assertFalse(sut.stop())
    }

    @Test
    fun `stop should return false if finished`() = test {
        val executions = 1
        val duration = 1

        val itemSet = ItemSet.Timed.Seconds(1)
        val sut = createSUT(
            List(executions) {
                WorkoutDomainModule.createTimedItemExecution(
                    item = Exercise("Test $it"),
                    sets = List(duration) { itemSet },
                )
            },
        )

        sut.start(this)

        sut.state.filterIsInstance<ItemsExecutionState.Finished>().test {
            awaitItem()
            assertFalse(sut.stop())
        }
    }

    @Test
    fun `stop should stop execution if started`() = test {
        val executions = 1
        val duration = 1

        val itemSet = ItemSet.Timed.Seconds(1)
        val sut = createSUT(
            List(executions) {
                WorkoutDomainModule.createTimedItemExecution(
                    item = Exercise("Test $it"),
                    sets = List(duration) { itemSet },
                )
            },
        )

        sut.start(this)

        sut.state.test {
            val state = awaitItem()
            if (state is ItemsExecutionState.Started) {
                assertTrue(sut.stop())

                val finishedItem = awaitItem() as ItemsExecutionState.Finished
                assertFalse(finishedItem.completed)
            }
        }
    }

    @Test
    fun `stop should stop execution if running`() = test {
        val executions = 1
        val duration = 2

        val itemSet = ItemSet.Timed.Seconds(1)
        val sut = createSUT(
            List(executions) {
                WorkoutDomainModule.createTimedItemExecution(
                    item = Exercise("Test $it"),
                    sets = List(duration) { itemSet },
                )
            },
        )

        sut.start(this)
        sut.state
            .filter{  it is ItemsExecutionState.Running || it is ItemsExecutionState.Finished }
            .test {
                var runTest = true
                do {
                    when(val currState = awaitItem()) {
                        is ItemsExecutionState.Running -> {
                            assertTrue(sut.stop())
                        }

                        is ItemsExecutionState.Finished -> {
                            runTest = false
                            assertFalse(currState.completed)
                            expectNoEvents()
                        }

                        else -> {
                            fail("Unexpected state: $currState")
                        }
                    }
                } while (runTest)
        }
    }

    @Test
    fun `stop should stop execution if paused`() = test {
        val executions = 1
        val duration = 2

        val itemSet = ItemSet.Timed.Seconds(1)
        val sut = createSUT(
            List(executions) {
                WorkoutDomainModule.createTimedItemExecution(
                    item = Exercise("Test $it"),
                    sets = List(duration) { itemSet },
                )
            },
        )

        sut.start(this)
        sut.state
            .filter {  it is ItemsExecutionState.Paused
                    || it is ItemsExecutionState.Running
                    || it is ItemsExecutionState.Finished
            }.test {
                var runTest = true
                do {
                    when(val currState = awaitItem()) {
                        is ItemsExecutionState.Running -> {
                            assertTrue(sut.pause())
                        }

                        is ItemsExecutionState.Paused -> {
                            assertTrue(sut.stop())
                        }

                        is ItemsExecutionState.Finished -> {
                            runTest = false
                            assertFalse(currState.completed)
                            expectNoEvents()
                        }

                        else -> {
                            fail("Unexpected state: $currState")
                        }
                    }
                } while (runTest)
            }

    }

    // TODO: test for repetitive execution when implemented

    private fun createSUT(items: List<ItemExecuting<*, *>>): ItemsExecution {
        return WorkoutDomainModule.createItemsExecution(items)
    }
}
