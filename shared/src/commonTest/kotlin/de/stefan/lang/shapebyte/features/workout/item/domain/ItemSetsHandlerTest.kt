package de.stefan.lang.shapebyte.features.workout.item.domain

import app.cash.turbine.test
import de.stefan.lang.shapebyte.features.workout.item.data.Exercise
import de.stefan.lang.shapebyte.features.workout.item.data.ItemSet
import de.stefan.lang.shapebyte.features.workout.item.data.ItemSetData
import de.stefan.lang.shapebyte.utils.BaseCoroutineTest
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.time.Duration.Companion.seconds

class ItemSetsHandlerTest : BaseCoroutineTest() {

    private val sut: ItemSetsHandler by inject()

    @Test
    fun `Test correct initial state`() = test {
        sut.stateFlow.test {
            assertEquals(ItemSetsState.Idle, awaitItem())
            expectNoEvents()
        }
    }

    @Test
    fun `start on empty item sets should call finish`() = test {
        sut.start(emptyList(), this)

        sut.stateFlow.test {
            assertEquals(ItemSetsState.Finished, awaitItem())
        }
    }

    @Test
    fun `Timed sets should emit correct states`() = test {
        val exercise = Exercise("Dummy")
        val sets = listOf(
            ItemSet.Timed(1.seconds, exercise),
            ItemSet.Timed(2.seconds, exercise),
            ItemSet.Timed(3.seconds, exercise),
        )

        sut.start(sets, this)

        sut.stateFlow.test {
            assertEquals(ItemSetsState.Started(sets.count()), awaitItem())

            for (i in 0 until sets.count()) {
                assertIs<ItemSetsState.Running.SetStarted>(awaitItem())

                repeat(sets[i].duration.inWholeSeconds.toInt() - 1) {
                    assertIs<ItemSetsState.Running>(awaitItem())
                }

                assertIs<ItemSetsState.Running.SetFinished>(awaitItem())
            }

            assertIs<ItemSetsState.Finished>(awaitItem())
            expectNoEvents()
        }
    }

    @Test
    fun `Repetition sets should emit correct states`() = test {
        val exercise = Exercise("Dummy")
        val sets = listOf(
            ItemSet.Repetition(exercise),
            ItemSet.Repetition(exercise),
            ItemSet.Repetition(exercise),
        )

        sut.start(sets, this)

        sut.stateFlow.test {
            assertEquals(ItemSetsState.Started(sets.count()), awaitItem())
            
            for (i in 1..sets.count()) {
                val reps = i.toUInt()
                sut.setInputValue(ItemSetWithInputValue.Repetitions(reps))

                val item = awaitItem()
                var current: ItemSetsState.Running = item as ItemSetsState.Running.SetFinished
                assertEquals(i - 1, current.currentSetIndex)
                assertEquals(sets.count(), current.totalSets)

                assertEquals(reps, (current.setData as ItemSetData.Repetitions).repetitionsDone)

                if (i < sets.count()) {
                    current = awaitItem() as ItemSetsState.Running.SetStarted
                    assertIs<ItemSetsState.Running.SetStarted>(current)
                    assertEquals(0u, (current.setData as ItemSetData.Repetitions).repetitionsDone)
                }
            }

            assertIs<ItemSetsState.Finished>(awaitItem())
            expectNoEvents()
        }
    }
}
