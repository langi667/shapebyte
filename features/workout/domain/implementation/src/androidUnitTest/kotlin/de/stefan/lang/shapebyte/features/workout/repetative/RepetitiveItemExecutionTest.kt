package de.stefan.lang.shapebyte.features.workout.repetative

import de.stefan.lang.shapebyte.features.workout.domain.test.BaseTest
import de.stefan.lang.coreutils.contract.progress.Progress
import de.stefan.lang.shapebyte.features.workout.data.contract.exercise.Exercise
import de.stefan.lang.shapebyte.features.workout.data.contract.item.Item
import de.stefan.lang.shapebyte.features.workout.data.contract.item.ItemSet
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.ItemExecutionState
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.RepetitiveItemExecution
import de.stefan.lang.shapebyte.features.workout.domain.implementation.repetative.RepetitiveItemExecutionImpl
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.RepetitiveItemExecutionData
import de.stefan.lang.utils.logging.LoggingModule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class RepetitiveItemExecutionTest : BaseTest() {
    private val item = Exercise("Test")

    @Test
    fun `initial state`() {
        val sets = listOf(ItemSet.Repetition())
        val sut = createSUT(item, sets)

        assertFalse(sut.isRunning)
        assertEquals(ItemExecutionState.Idle, sut.state.value)
        assertEquals(item, sut.item)

        assertEquals(sets, sut.sets)
        assertEquals(0u, sut.setRepsPerformed)
        assertEquals(0u, sut.totalRepsPerformed)

        assertNull(sut.totalRepsRemaining)
        assertNull(sut.totalRepsGoal)
    }

    @Test
    fun `should emit correct states when starting without max reps`() = test {
        val numberOfSets = 3
        val sut = createSUT(item, numberOfSets)
        assertTrue(sut.start(this))

        for (i in 0 until numberOfSets) {
            val repCount = 10
            sut.setInputValue(repCount.toUInt())

            if (i < numberOfSets - 1) {
                val totalReps = (i + 1) * repCount

                assertEquals(
                    ItemExecutionState.SetStarted(
                        item = item,
                        set = ItemSet.Repetition(),
                        progress = Progress.ZERO,
                        totalProgress = Progress.with(i + 1, 3),
                        setData = RepetitiveItemExecutionData(
                            repsPerSetPerformed = 0u,
                            totalRepsPerformed = totalReps.toUInt(),
                        ),
                    ),
                    sut.state.value,
                )
            } else {
                assertEquals(ItemExecutionState.Finished(item, true), sut.state.value)
            }
        }
    }

    @Test
    fun `Progress should be computed based on set count when one item has no max reps`() = test {
        val sets = listOf(
            ItemSet.Repetition(10u),
            ItemSet.Repetition(),
            ItemSet.Repetition(10u),
        )

        val sut = createSUT(item, sets)
        assertTrue(sut.start(this))
        sut.setInputValue(15u)

        val state = sut.state.value as ItemExecutionState.Running<RepetitiveItemExecutionData>
        assertEquals(Progress.with(1, sets.size), state.totalProgress)
    }

    @Test
    fun `Progress should be computed based on reps performed when max reps are set`() = test {
        val sets = listOf(
            ItemSet.Repetition(10u),
            ItemSet.Repetition(10u),
            ItemSet.Repetition(10u),
        )

        val sut = createSUT(item, sets)
        assertTrue(sut.start(this))
        sut.setInputValue(15u)

        val state = sut.state.value as ItemExecutionState.Running<RepetitiveItemExecutionData>
        assertEquals(Progress(0.5f), state.totalProgress)
    }

    @Test
    fun `total sets count`() = test {
        val sets = listOf(
            ItemSet.Repetition(10u),
            ItemSet.Repetition(10u),
            ItemSet.Repetition(10u),
        )

        val total = sets.sumOf { it.repetitions?.toInt() ?: 0 }.toUInt()
        val sut = createSUT(item, sets)

        assertTrue(sut.start(this))
        sut.setInputValue(10u)

        var state = sut.state.value as ItemExecutionState.Running<RepetitiveItemExecutionData>
        assertEquals(total, state.setData.totalRepsGoal)
        assertEquals(20u, state.setData.totalRepsRemaining)

        sut.setInputValue(5u)
        state = sut.state.value as ItemExecutionState.Running<RepetitiveItemExecutionData>
        assertEquals(15u, state.setData.totalRepsRemaining)

        sut.setInputValue(5u)
        state = sut.state.value as ItemExecutionState.Running<RepetitiveItemExecutionData>
        assertEquals(10u, state.setData.totalRepsRemaining)
    }

    @Test
    fun `should emit correct states when starting with max reps`() = test {
        val numberOfSets = 3
        val maxRepsPerSet = 10u

        val sets = List(numberOfSets) { ItemSet.Repetition(maxRepsPerSet) }
        val sut = createSUT(item, sets)

        assertTrue(sut.start(this))

        var currReps = 0u
        val repIncrease = maxRepsPerSet / 2u
        val totalRepGoal = sets.sumOf { it.repetitions?.toInt() ?: 0 }.toUInt()

        for (i in 0 until numberOfSets) {
            var inputVal = repIncrease
            sut.setInputValue(inputVal)
            currReps += inputVal

            var totalRepsRemaining = totalRepGoal - currReps
            assertEquals(
                ItemExecutionState.SetRunning(
                    item = item,
                    set = ItemSet.Repetition(maxRepsPerSet),
                    progress = Progress(0.5f),
                    totalProgress = Progress.with(
                        currReps,
                        totalRepGoal,
                    ),
                    setData = RepetitiveItemExecutionData(
                        repsPerSetPerformed = repIncrease,
                        totalRepsPerformed = currReps,
                        totalRepsRemaining = totalRepsRemaining,
                        totalRepsGoal = totalRepGoal,
                    ),
                ),
                sut.state.value,
            )

            inputVal = repIncrease * 2u
            sut.setInputValue(inputVal)
            currReps += inputVal

            totalRepsRemaining = totalRepGoal - currReps

            if (i < numberOfSets - 1) {
                assertEquals(
                    ItemExecutionState.SetStarted(
                        item = item,
                        set = ItemSet.Repetition(maxRepsPerSet),
                        progress = Progress.ZERO,
                        totalProgress = Progress.with(
                            currReps,
                            totalRepGoal,
                        ),
                        setData = RepetitiveItemExecutionData(
                            repsPerSetPerformed = 0u,
                            totalRepsPerformed = currReps,
                            totalRepsRemaining = totalRepsRemaining,
                            totalRepsGoal = totalRepGoal,
                        ),
                    ),
                    sut.state.value,
                )
            } else {
                assertEquals(ItemExecutionState.Finished(item, true), sut.state.value)
            }
        }
    }

    @Test
    fun `should emit correct states when starting with max reps and different rep goal`() =
        test {
            val sets = listOf(
                ItemSet.Repetition(10u),
                ItemSet.Repetition(20u),
            )

            val sut = createSUT(item, sets)
            assertTrue(sut.start(this))

            val totalRepGoal = sets.sumOf { it.repetitions?.toInt() ?: 0 }.toUInt()

            var currSet = sets[0]
            var repsPerformedTotal = 0u
            var setRepetition = currSet.repetitions!!

            assertEquals(currSet, sut.currentSet)

            // First run, increase to 5 reps
            var progress = 0.5f
            var repsPerformedPerSet = (setRepetition.toFloat() * progress).toUInt()

            sut.setInputValue(repsPerformedPerSet)
            repsPerformedTotal += repsPerformedPerSet
            var totalRepsRemaining = totalRepGoal - repsPerformedTotal

            assertEquals(
                // should be running since rep goal not reached
                ItemExecutionState.SetRunning(
                    item = item,
                    set = ItemSet.Repetition(setRepetition),
                    progress = Progress(progress),
                    totalProgress = Progress.with(
                        repsPerformedTotal,
                        totalRepGoal,
                    ),
                    setData = RepetitiveItemExecutionData(
                        repsPerSetPerformed = repsPerformedPerSet,
                        totalRepsPerformed = repsPerformedTotal,
                        totalRepsRemaining = totalRepsRemaining,
                        totalRepsGoal = totalRepGoal,
                    ),
                ),
                sut.state.value,
            )

            assertEquals(currSet, sut.currentSet)

            // Increase to 10 reps, means first set is done, expect second set to start
            repsPerformedPerSet = setRepetition
            sut.setInputValue(repsPerformedPerSet)
            repsPerformedTotal += repsPerformedPerSet
            totalRepsRemaining = totalRepGoal - repsPerformedTotal

            currSet = sets[1]
            setRepetition = currSet.repetitions!!

            assertEquals(
                // started next set but with zero progress
                ItemExecutionState.SetStarted(
                    item = item,
                    set = currSet,
                    progress = Progress.ZERO,
                    totalProgress = Progress.with(
                        repsPerformedTotal,
                        totalRepGoal,
                    ),
                    setData = RepetitiveItemExecutionData(
                        repsPerSetPerformed = 0u, // nothing performed yet
                        totalRepsPerformed = repsPerformedTotal,
                        totalRepsRemaining = totalRepsRemaining,
                        totalRepsGoal = totalRepGoal,
                    ),
                ),
                sut.state.value,
            )

            assertEquals(currSet, sut.currentSet)
            // increase to 16 reps, means second set is running
            progress = 0.8f
            repsPerformedPerSet = (setRepetition.toFloat() * progress).toUInt()

            sut.setInputValue(repsPerformedPerSet)
            repsPerformedTotal += repsPerformedPerSet
            totalRepsRemaining = totalRepGoal - repsPerformedTotal

            assertEquals(
                // should be running since rep goal not reached
                ItemExecutionState.SetRunning(
                    item = item,
                    set = ItemSet.Repetition(setRepetition),
                    progress = Progress(progress),
                    totalProgress = Progress.with(
                        repsPerformedTotal,
                        totalRepGoal,
                    ),
                    setData = RepetitiveItemExecutionData(
                        repsPerSetPerformed = repsPerformedPerSet,
                        totalRepsPerformed = repsPerformedTotal,
                        totalRepsRemaining = totalRepsRemaining,
                        totalRepsGoal = totalRepGoal,
                    ),
                ),
                sut.state.value,
            )

            assertEquals(currSet, sut.currentSet)

            // increase to 20 reps, means second set and therefor all sets are done
            sut.setInputValue(setRepetition)
            assertEquals(ItemExecutionState.Finished(item, true), sut.state.value)
        }

    @Test
    fun `should not start twice`() = test {
        val numberOfSets = 3
        val sut = createSUT(item, numberOfSets)
        assertTrue(sut.start(this))
        assertFalse(sut.start(this))
    }

    @Test
    fun `should call finish if no sets specified`() = test {
        val sut = createSUT(item, emptyList())

        assertTrue(sut.start(this))
        assertEquals(ItemExecutionState.Finished(item, true), sut.state.value)
    }

    private fun createSUT(
        item: Item,
        numberOfSets: Int,
    ): RepetitiveItemExecutionImpl {
        val sets = List(numberOfSets) { ItemSet.Repetition() }
        return createSUT(item, sets)
    }

    private fun createSUT(
        item: Item,
        sets: List<ItemSet.Repetition>,
    ) = RepetitiveItemExecutionImpl(item, sets, LoggingModule.logger())
}
