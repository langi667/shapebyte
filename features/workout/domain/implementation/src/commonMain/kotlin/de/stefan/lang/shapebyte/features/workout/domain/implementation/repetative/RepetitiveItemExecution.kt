package de.stefan.lang.shapebyte.features.workout.domain.implementation.repetative

import de.stefan.lang.coreutils.contract.progress.Progress
import de.stefan.lang.shapebyte.features.workout.data.contract.item.Item
import de.stefan.lang.shapebyte.features.workout.data.contract.item.ItemSet
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.ItemExecutionState
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.RepetitiveItemExecuting
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.RepetitiveItemExecutionData
import de.stefan.lang.utils.logging.contract.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RepetitiveItemExecution(
    override val item: Item,
    override val sets: List<ItemSet.Repetition>,
    override val logger: Logger,
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
            sets.sumOf { it.repetitions?.toInt() ?: 0 }.toUInt()
        }

    val totalRepsRemaining: UInt?
        get() = totalRepsRemaining(totalRepsPerformed)

    private val hasSetWithoutMaxReps = sets.firstOrNull { it.repetitions == null } != null
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

        val set = currentSet ?: run {
            logE("Invalid state, Expected currentSet to be set, but it is not")
            return
        }

        val maxRepetitions = set.repetitions ?: 0u
        val nextTotalRepsPerformed = totalRepsPerformed + value
        val nextTotalRepsRemaining = totalRepsRemaining(nextTotalRepsPerformed)

        if (value >= maxRepetitions) {
            val progress = computeTotalProgress(setIndex, nextTotalRepsPerformed)
            _state.value = ItemExecutionState.SetFinished(
                item = item,
                set = set,
                progress = Progress.Companion.ZERO,
                totalProgress = progress,
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
                progress = Progress.Companion.with(value.toInt(), maxRepetitions.toInt()),
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

    override fun pause(): Boolean {
        // TODO: implement
        return false
    }

    override fun stop(): Boolean {
        // TODO: implement
        return false
    }

    private fun startSetForIndex(index: UInt) {
        if (index.toInt() >= sets.size) {
            _state.value = ItemExecutionState.Finished(item, true)
            return
        }

        currentSetIndex = index

        _state.value = ItemExecutionState.SetStarted(
            item = item,
            set = sets[index.toInt()],
            progress = Progress.Companion.ZERO,
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
            return Progress.Companion.COMPLETE
        }

        val progress = totalRepsGoal?.let {
            Progress.Companion.with(totalRepsPerformed.toInt(), it.toInt())
        } ?: Progress.Companion.with(currentSetIndex.toInt(), sets.size)

        return progress
    }

    private fun totalRepsRemaining(totalRepsPerformed: UInt): UInt? {
        val totalRepGoal = totalRepsGoal ?: return null
        return totalRepGoal - totalRepsPerformed
    }
}
