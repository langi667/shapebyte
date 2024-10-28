package de.stefan.lang.shapebyte.features.workout.item.domain.repetitive

import de.stefan.lang.shapebyte.features.workout.item.data.Item
import de.stefan.lang.shapebyte.features.workout.item.data.ItemSet
import de.stefan.lang.shapebyte.features.workout.item.domain.ItemExecutionState
import de.stefan.lang.shapebyte.features.workout.item.domain.RepetitiveItemExecuting
import de.stefan.lang.shapebyte.utils.Progress
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class RepetitiveItemExecutionData(
    val repsPerSetPerformed: UInt,
    val totalRepsPerformed: UInt,
    val totalRepsRemaining: UInt? = null,
    val totalRepsGoal: UInt? = null,
)

class RepetitiveItemExecution(
    override val item: Item,
    override val sets: List<ItemSet.Repetition>,
    override val logger: Logging,
) : RepetitiveItemExecuting {

    private val _state: MutableStateFlow<ItemExecutionState<RepetitiveItemExecutionData>> =
        MutableStateFlow(ItemExecutionState.Idle)

    override val state: StateFlow<ItemExecutionState<RepetitiveItemExecutionData>> get() = _state

    val setRepsPerformed: UInt
        get() = when (val currState = state.value) {
            is ItemExecutionState.Running -> currState.setData.repsPerSetPerformed
            else -> 0u
        }

    val totalRepsPerformed: UInt
        get() = when (val currState = state.value) {
            is ItemExecutionState.Running -> currState.setData.totalRepsPerformed
            else -> 0u
        }

    val totalRepsGoal: UInt?
        get() = if (hasSetWithoutMaxReps) {
            null
        } else {
            sets.sumOf { it.maxRepetitions?.toInt() ?: 0 }.toUInt()
        }

    val totalRepsRemaining: UInt?
        get() = totalRepsRemaining(totalRepsPerformed)

    private val hasSetWithoutMaxReps = sets.firstOrNull { it.maxRepetitions == null } != null
    private var currentSetIndex: UInt? = null

    override fun setInputValue(value: UInt) {
        if (!isRunning) { // TODO: check if this is correct / can automatically start
            logE("Cannot add value to non started execution, call start first")
            return
        }

        val setIndex = currentSetIndex ?: run {
            logE("Invalid state, Expected currentSetIndex to be set, but it is not")
            return
        }

        val set = sets[setIndex.toInt()]
        val maxRepetitions = set.maxRepetitions ?: 0u

        val nextTotalRepsPerformed = totalRepsPerformed + value
        val nextTotalRepsRemaining = totalRepsRemaining(nextTotalRepsPerformed)

        if (value >= maxRepetitions) {
            _state.value = ItemExecutionState.SetFinished(
                item = item,
                set = set,
                setProgress = Progress.ZERO,
                totalProgress = computeTotalProgress(setIndex, nextTotalRepsPerformed),
                setData = RepetitiveItemExecutionData(
                    repsPerSetPerformed = value,
                    totalRepsPerformed = nextTotalRepsPerformed,
                    totalRepsRemaining = nextTotalRepsRemaining,
                    totalRepsGoal = totalRepsGoal,
                ),
            )

            val nextSet = setIndex + 1u
            startSetForIndex(nextSet)
        } else {
            _state.value = ItemExecutionState.SetRunning(
                item = item,
                set = set,
                setProgress = Progress.with(value.toInt(), maxRepetitions.toInt()),
                totalProgress = computeTotalProgress(setIndex, nextTotalRepsPerformed),
                setData = RepetitiveItemExecutionData(
                    repsPerSetPerformed = value,
                    totalRepsPerformed = nextTotalRepsPerformed,
                    totalRepsRemaining = nextTotalRepsRemaining,
                    totalRepsGoal = totalRepsGoal,
                ),
            )
        }
    }

    override fun start(scope: CoroutineScope): Boolean {
        if (isRunning) {
            logE("Cannot start a new set while another one is running")
            return false
        }

        _state.value = ItemExecutionState.Started(item)
        startSetForIndex(0u)

        return true
    }

    private fun startSetForIndex(index: UInt) {
        if (index.toInt() >= sets.size) {
            _state.value = ItemExecutionState.Finished(item)
            return
        }

        currentSetIndex = index

        _state.value = ItemExecutionState.SetStarted(
            item = item,
            set = sets[index.toInt()],
            setProgress = Progress.ZERO,
            totalProgress = computeTotalProgress(index, totalRepsPerformed),
            setData = RepetitiveItemExecutionData(
                repsPerSetPerformed = 0u,
                totalRepsPerformed = totalRepsPerformed,
                totalRepsRemaining = totalRepsRemaining,
                totalRepsGoal = totalRepsGoal,
            ),
        )
    }

    /**
     *  If at least one set without max repetitions is present,the progress is calculated based
     *  on sets done. Otherwise we compute progress using the performed reps / overall max reps
     */
    private fun computeTotalProgress(
        currentSetIndex: UInt,
        totalRepsPerformed: UInt,
    ): Progress {
        if (sets.isEmpty()) {
            return Progress.COMPLETE
        }

        val progress = totalRepsGoal?.let {
            Progress.with(totalRepsPerformed.toInt(), it.toInt())
        } ?: Progress.with(currentSetIndex.toInt(), sets.size)

        return progress
    }

    private fun totalRepsRemaining(totalRepsPerformed: UInt): UInt? {
        val totalRepGoal = totalRepsGoal ?: return null
        return totalRepGoal - totalRepsPerformed
    }
}
