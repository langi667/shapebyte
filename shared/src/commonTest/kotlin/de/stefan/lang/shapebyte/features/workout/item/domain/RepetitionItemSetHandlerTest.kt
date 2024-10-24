package de.stefan.lang.shapebyte.features.workout.item.domain

import app.cash.turbine.test
import de.stefan.lang.shapebyte.features.workout.di.WorkoutModule
import de.stefan.lang.shapebyte.features.workout.item.data.Exercise
import de.stefan.lang.shapebyte.features.workout.item.data.ItemSet
import de.stefan.lang.shapebyte.features.workout.item.data.ItemSetData
import de.stefan.lang.shapebyte.features.workout.item.data.ItemSetState
import de.stefan.lang.shapebyte.utils.BaseCoroutineTest
import de.stefan.lang.shapebyte.utils.Progress
import org.koin.core.component.get
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIsNot
import kotlin.test.assertNull

class RepetitionItemSetHandlerTest : BaseCoroutineTest() {

    @Test
    fun `initial state`() = test {
        val sut: RepetitionItemSetHandler = WorkoutModule.get()
        assertNull(sut.repetitionsDone)
        assertNull(sut.set)
    }

    @Test
    fun `start should emit Started state`() = test {
        val sut: RepetitionItemSetHandler = WorkoutModule.get()
        sut.start(ItemSet.Repetition(Exercise("Test")), this)

        sut.stateFlow.test {
            assertEquals(
                expected = ItemSetState.Started(ItemSetData.Repetitions(0u, Progress.ZERO)),
                actual = awaitItem(),
            )
        }
    }

    @Test
    fun `start twice should not start again`() = test {
        val sut: RepetitionItemSetHandler = WorkoutModule.get()

        val set = ItemSet.Repetition(Exercise("Test"))
        sut.start(set, this)

        sut.stateFlow.test {
            assertEquals(
                expected = ItemSetState.Started(ItemSetData.Repetitions(0u, Progress.ZERO)),
                actual = awaitItem(),
            )
        }

        sut.start(ItemSet.Repetition(Exercise("Test 1232323")), this)
        assertEquals(set, sut.set)
    }

    @Test
    fun `setRepetitions should emit Finished state`() = test {
        val sut: RepetitionItemSetHandler = WorkoutModule.get()
        val set = ItemSet.Repetition(Exercise("Test"))
        sut.start(set, this)

        sut.stateFlow.test {
            assertEquals(
                expected = ItemSetState.Started(ItemSetData.Repetitions(0u, Progress.ZERO)),
                actual = awaitItem(),
            )
        }

        val reps = 10u
        sut.setInputValue(reps)
        assertEquals(reps, sut.repetitionsDone)

        sut.stateFlow.test {
            assertEquals(
                expected = ItemSetState.Finished(ItemSetData.Repetitions(reps, Progress.COMPLETE)),
                actual = awaitItem(),
            )
        }
    }

    @Test
    fun `should not be able to set repetitions when finished`() = test {
        val sut: RepetitionItemSetHandler = WorkoutModule.get()

        val set = ItemSet.Repetition(Exercise("Test"))
        sut.start(set, this)

        val reps = 10u
        sut.setInputValue(reps)

        sut.stateFlow.test {
            assertEquals(
                expected = ItemSetState.Finished(ItemSetData.Repetitions(reps, Progress.COMPLETE)),
                actual = awaitItem(),
            )
        }

        sut.setInputValue(reps * 2u)
        assertEquals(
            expected = sut.repetitionsDone,
            actual = reps,
        )
    }

    @Test
    fun `should not be able to set repetitions if not started `() {
        val sut: RepetitionItemSetHandler = WorkoutModule.get()

        sut.setInputValue(10u)
        assertNull(sut.repetitionsDone)
    }

    @Test
    fun `should not be able to set repetitions if paused`() = test {
        val sut: RepetitionItemSetHandler = WorkoutModule.get()

        val set = ItemSet.Repetition(Exercise("Test"))
        sut.start(set, this)

        sut.stateFlow.test {
            assertEquals(
                expected = ItemSetState.Started(ItemSetData.Repetitions(0u, Progress.ZERO)),
                actual = awaitItem(),
            )
        }

        sut.pause()

        sut.setInputValue(10u)
        assertEquals(0u, sut.repetitionsDone)
    }

    @Test
    fun `pause should emit paused state`() = test {
        val sut: RepetitionItemSetHandler = WorkoutModule.get()
        val set = ItemSet.Repetition(Exercise("Test"))

        sut.start(set, this)

        sut.stateFlow.test {
            assertEquals(
                expected = ItemSetState.Started(ItemSetData.Repetitions(0u, Progress.ZERO)),
                actual = awaitItem(),
            )
        }

        sut.pause()

        sut.stateFlow.test {
            assertEquals(
                expected = ItemSetState.Paused(ItemSetData.Repetitions(0u, Progress.ZERO)),
                actual = awaitItem(),
            )
        }
    }

    @Test
    fun `pause should not emit if state is not running`() = test {
        val sut: RepetitionItemSetHandler = WorkoutModule.get()
        val set = ItemSet.Repetition(Exercise("Test"))

        sut.pause()

        sut.stateFlow.test { assertIsNot<ItemSetState.Paused>(awaitItem()) }

        sut.start(set, this)
        sut.setInputValue(10u)
        sut.pause()

        sut.stateFlow.test { assertIsNot<ItemSetState.Paused>(awaitItem()) }
    }

    @Test
    fun `resume should emit started after paused`() = test {
        val sut: RepetitionItemSetHandler = WorkoutModule.get()
        val set = ItemSet.Repetition(Exercise("Test"))

        sut.start(set, this)

        sut.stateFlow.test {
            assertEquals(
                expected = ItemSetState.Started(ItemSetData.Repetitions(0u, Progress.ZERO)),
                actual = awaitItem(),
            )
        }

        sut.pause()

        sut.stateFlow.test {
            assertEquals(
                expected = ItemSetState.Paused(ItemSetData.Repetitions(0u, Progress.ZERO)),
                actual = awaitItem(),
            )
        }

        sut.resume(this)

        sut.stateFlow.test {
            assertEquals(
                expected = ItemSetState.Started(ItemSetData.Repetitions(0u, Progress.ZERO)),
                actual = awaitItem(),
            )
        }

        val reps = 10u
        sut.setInputValue(reps)

        sut.stateFlow.test {
            assertEquals(
                expected = ItemSetState.Finished(ItemSetData.Repetitions(reps, Progress.COMPLETE)),
                actual = awaitItem(),
            )
        }
    }

    @Test
    fun `resume should not emit if state is not running`() = test {
        val sut: RepetitionItemSetHandler = WorkoutModule.get()
        val set = ItemSet.Repetition(Exercise("Test"))

        sut.resume(this)

        sut.stateFlow.test {
            assertFalse(awaitItem().isRunning)
        }

        sut.start(set, this)
        sut.setInputValue(10u)
        sut.resume(this)

        sut.stateFlow.test {
            assertFalse(awaitItem().isRunning)
        }
    }
}
