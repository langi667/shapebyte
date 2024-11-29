package de.stefan.lang.shapebyte.features.workout.item.core.domain

import app.cash.turbine.test
import de.stefan.lang.shapebyte.di.DPI
import de.stefan.lang.shapebyte.features.workout.item.core.data.Exercise
import de.stefan.lang.shapebyte.features.workout.item.core.data.Item
import de.stefan.lang.shapebyte.features.workout.item.core.data.ItemSet
import de.stefan.lang.shapebyte.features.workout.item.timed.domain.TimedItemExecutionData
import de.stefan.lang.shapebyte.utils.BaseCoroutineTest
import de.stefan.lang.shapebyte.utils.Progress
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs

class ItemsExecutionTest : BaseCoroutineTest() {
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

        val sut = createSUT(
            List(executions) {
                DPI.createTimedItemExecution(
                    item = Exercise("Test $it"),
                    sets = List(duration) { ItemSet.Timed.Seconds(1) },
                )
            },
        )

        sut.start(this)
        sut.state.test {
            assertIs<ItemsExecutionState.Started>(awaitItem())

            for (currExecution in 0..<executions) {
                for (currDuration in 0..<duration) {
                    var runningState = awaitItem() as ItemsExecutionState.Running
                    assertEquals(Exercise("Test $currExecution"), runningState.item)
                    assertEquals(currExecution, runningState.itemIndex)
                    assertEquals(executions, runningState.itemCount)

                    var totalProgress = Progress(currDuration.toFloat() / duration.toFloat())
                    val currSet = ItemSet.Timed.Seconds(1)
                    val itemStateStart = runningState.itemState as ItemExecutionState.SetStarted

                    assertEquals(Progress.ZERO, itemStateStart.progress)
                    assertEquals(totalProgress, itemStateStart.totalProgress)
                    assertEquals(currSet, itemStateStart.set)

                    runningState = awaitItem() as ItemsExecutionState.Running
                    assertEquals(Exercise("Test $currExecution"), runningState.item)

                    val itemStateFinished = runningState.itemState as ItemExecutionState.SetFinished
                    totalProgress = Progress((currDuration + 1).toFloat() / duration.toFloat())

                    assertEquals(Progress.COMPLETE, itemStateFinished.progress)
                    assertEquals(totalProgress, itemStateFinished.totalProgress)
                    assertEquals(currSet, itemStateFinished.set)
                }
            }

            assertIs<ItemsExecutionState.Finished>(awaitItem())
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
            DPI.createTimedItemExecution(
                item = Exercise("Test $it"),
                sets = List(duration) { ItemSet.Timed.Seconds(1) },
            )
        } + listOf(
            TimedFailing(
                item = ignoredItem,
                sets = List(duration) { ItemSet.Timed.Seconds(1) },
                DPI.logger(),
            ),
        ) +
            List(executionsSucceedAfter) {
                DPI.createTimedItemExecution(
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

    // TODO: test for repetitive execution when implemented

    private fun createSUT(items: List<ItemExecuting<*, *>>): ItemsExecution {
        return DPI.createItemsExecution(items)
    }
}
