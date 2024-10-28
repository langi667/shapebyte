package de.stefan.lang.shapebyte.features.workout.item.domain

import app.cash.turbine.test
import de.stefan.lang.shapebyte.features.workout.di.WorkoutModule
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
        assertEquals(Progress.ZERO, sut.progress)
    }

    @Test
    fun `start should emit Started state`() = test {
        val sut: RepetitionItemSetHandler = WorkoutModule.get()
        sut.start(ItemSet.Repetition(), this)

        sut.stateFlow.test {
            assertEquals(
                expected = ItemSetState.Started(ItemSetData.Repetitions(0u, null, Progress.ZERO)),
                actual = awaitItem(),
            )
        }
    }

    @Test
    fun `start twice should not start again`() = test {
        val sut: RepetitionItemSetHandler = WorkoutModule.get()

        val set = ItemSet.Repetition()
        sut.start(set, this)

        sut.stateFlow.test {
            assertEquals(
                expected = ItemSetState.Started(ItemSetData.Repetitions(0u, null, Progress.ZERO)),
                actual = awaitItem(),
            )
        }

        sut.start(ItemSet.Repetition(), this)
        assertEquals(set, sut.set)
    }

    @Test
    fun `setRepetitions should emit Finished state`() = test {
        val sut: RepetitionItemSetHandler = WorkoutModule.get()
        val set = ItemSet.Repetition()
        sut.start(set, this)

        sut.stateFlow.test {
            assertEquals(
                expected = ItemSetState.Started(ItemSetData.Repetitions(0u, null, Progress.ZERO)),
                actual = awaitItem(),
            )
        }

        val reps = 10u
        sut.setInputValue(reps)
        assertEquals(reps, sut.repetitionsDone)

        sut.stateFlow.test {
            assertEquals(
                expected = ItemSetState.Finished(ItemSetData.Repetitions(reps, null, Progress.COMPLETE)),
                actual = awaitItem(),
            )
        }
    }

    @Test
    fun `should not be able to set repetitions when finished`() = test {
        val sut: RepetitionItemSetHandler = WorkoutModule.get()

        val set = ItemSet.Repetition()
        sut.start(set, this)

        val reps = 10u
        sut.setInputValue(reps)

        sut.stateFlow.test {
            assertEquals(
                expected = ItemSetState.Finished(ItemSetData.Repetitions(reps, null, Progress.COMPLETE)),
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

        val set = ItemSet.Repetition()
        sut.start(set, this)

        sut.stateFlow.test {
            assertEquals(
                expected = ItemSetState.Started(ItemSetData.Repetitions(0u, null, Progress.ZERO)),
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
        val set = ItemSet.Repetition()

        sut.start(set, this)

        sut.stateFlow.test {
            assertEquals(
                expected = ItemSetState.Started(ItemSetData.Repetitions(0u, null, Progress.ZERO)),
                actual = awaitItem(),
            )
        }

        sut.pause()

        sut.stateFlow.test {
            assertEquals(
                expected = ItemSetState.Paused(ItemSetData.Repetitions(0u, null, Progress.ZERO)),
                actual = awaitItem(),
            )
        }
    }

    @Test
    fun `pause should not emit if state is not running`() = test {
        val sut: RepetitionItemSetHandler = WorkoutModule.get()
        val set = ItemSet.Repetition()

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
        val set = ItemSet.Repetition()

        sut.start(set, this)

        sut.stateFlow.test {
            assertEquals(
                expected = ItemSetState.Started(ItemSetData.Repetitions(0u, null, Progress.ZERO)),
                actual = awaitItem(),
            )
        }

        sut.pause()

        sut.stateFlow.test {
            assertEquals(
                expected = ItemSetState.Paused(ItemSetData.Repetitions(0u, null, Progress.ZERO)),
                actual = awaitItem(),
            )
        }

        sut.resume(this)

        sut.stateFlow.test {
            assertEquals(
                expected = ItemSetState.Started(ItemSetData.Repetitions(0u, null, Progress.ZERO)),
                actual = awaitItem(),
            )
        }

        val reps = 10u
        sut.setInputValue(reps)

        sut.stateFlow.test {
            assertEquals(
                expected = ItemSetState.Finished(ItemSetData.Repetitions(reps, null, Progress.COMPLETE)),
                actual = awaitItem(),
            )
        }
    }

    @Test
    fun `resume should not emit if state is not running`() = test {
        val sut: RepetitionItemSetHandler = WorkoutModule.get()
        val set = ItemSet.Repetition()

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

    @Test
    fun `Progress computation with rep goals`() = test {
        val sut: RepetitionItemSetHandler = WorkoutModule.get()
        val set = ItemSet.Repetition(repetitions = 10u)

        assertEquals(Progress.ZERO, sut.progress)
        sut.start(set, this)
        assertEquals(Progress.ZERO, sut.progress)

        sut.setInputValue(5u)
        assertEquals(Progress(0.5f), sut.progress)

        sut.setInputValue(8u)
        assertEquals(Progress(0.8f), sut.progress)

        sut.setInputValue(10u)
        assertEquals(Progress.COMPLETE, sut.progress)
    }

    @Test
    fun `Progress computation without rep goals`() = test {
        val sut: RepetitionItemSetHandler = WorkoutModule.get()
        val set = ItemSet.Repetition()

        assertEquals(Progress.ZERO, sut.progress)
        sut.start(set, this)
        assertEquals(Progress.ZERO, sut.progress)

        sut.setInputValue(5u)
        assertEquals(Progress.COMPLETE, sut.progress)
    }

    @Test
    fun `States with rep goals`() = test {
        val sut: RepetitionItemSetHandler = WorkoutModule.get()
        val repGoal = 10u
        val set = ItemSet.Repetition(repetitions = repGoal)

        assertEquals(ItemSetState.Idle, sut.stateFlow.value)
        sut.start(set, this)

        assertEquals(
            expected = ItemSetState.Started(
                setData = ItemSetData.Repetitions(
                    repetitionsDone = 0u,
                    repetitionGoal = repGoal,
                    progress = Progress.ZERO,
                ),
            ),
            actual = sut.stateFlow.value,
        )

        sut.setInputValue(5u)

        assertEquals(
            expected = ItemSetState.Running(
                setData = ItemSetData.Repetitions(
                    repetitionsDone = 5u,
                    repetitionGoal = repGoal,
                    progress = Progress(0.5f),
                ),
            ),
            actual = sut.stateFlow.value,
        )

        sut.setInputValue(8u)

        assertEquals(
            expected = ItemSetState.Running(
                setData = ItemSetData.Repetitions(
                    repetitionsDone = 8u,
                    repetitionGoal = repGoal,
                    progress = Progress(0.8f),
                ),
            ),
            actual = sut.stateFlow.value,
        )

        sut.pause()

        assertEquals(
            expected = ItemSetState.Paused(
                setData = ItemSetData.Repetitions(
                    repetitionsDone = 8u,
                    repetitionGoal = repGoal,
                    progress = Progress(0.8f),
                ),
            ),
            actual = sut.stateFlow.value,
        )

        sut.resume(this)

        assertEquals(
            expected = ItemSetState.Running(
                setData = ItemSetData.Repetitions(
                    repetitionsDone = 8u,
                    repetitionGoal = repGoal,
                    progress = Progress(0.8f),
                ),
            ),
            actual = sut.stateFlow.value,
        )

        sut.setInputValue(10u)

        assertEquals(
            expected = ItemSetState.Finished(
                setData = ItemSetData.Repetitions(
                    repetitionsDone = 10u,
                    repetitionGoal = repGoal,
                    progress = Progress.COMPLETE,
                ),
            ),
            actual = sut.stateFlow.value,
        )
    }
}
