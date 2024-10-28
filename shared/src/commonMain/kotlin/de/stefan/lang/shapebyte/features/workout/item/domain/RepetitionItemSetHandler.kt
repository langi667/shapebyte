package de.stefan.lang.shapebyte.features.workout.item.domain

import de.stefan.lang.shapebyte.features.workout.item.data.ItemSet
import de.stefan.lang.shapebyte.features.workout.item.data.ItemSetData
import de.stefan.lang.shapebyte.features.workout.item.data.ItemSetState
import de.stefan.lang.shapebyte.utils.Progress
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// TODO: remove
class RepetitionItemSetHandler(
    override val logger: Logging,
) : ItemSetWithInputValueHandling, Loggable {

    var set: ItemSet.Repetition? = null
        private set

    val repetitionsDone: UInt?
        get() {
            return when (val state = stateFlow.value) {
                is ItemSetState.Data -> (state.setData as? ItemSetData.Repetitions)?.repetitionsDone
                else -> null
            }
        }

    val repetitionGoal: UInt?
        get() = set?.repetitions

    val progress: Progress
        get() = computeProgress(repetitionsDone ?: 0u)

    private val _stateFlow = MutableStateFlow<ItemSetState>(ItemSetState.Idle)

    override val stateFlow: StateFlow<ItemSetState>
        get() = _stateFlow

    override fun start(set: ItemSet, scope: CoroutineScope) {
        if (!stateFlow.value.isStopped) {
            logD("Cannot start a new set while another one is running")
            return
        }

        when (set) {
            set as ItemSet.Repetition -> {
                this.set = set
            }
            else -> {
                logE("Unsupported set type for $tag: ${set::class.simpleName}")
                return
            }
        }

        _stateFlow.value = ItemSetState.Started(
            setData = ItemSetData.Repetitions(
                repetitionsDone = 0u,
                repetitionGoal = repetitionGoal,
                progress = Progress.ZERO,
            ),
        )
    }

    override fun pause() {
        if (!_stateFlow.value.isRunning) {
            logW("Cannot pause a set that is not running")
            return
        }

        _stateFlow.value = ItemSetState.Paused(
            setData = ItemSetData.Repetitions(
                repetitionsDone = repetitionsDone ?: 0u,
                repetitionGoal = repetitionGoal,
                progress = computeProgress(repetitionsDone ?: 0u),
            ),
        )
    }

    override fun resume(resumeScope: CoroutineScope) {
        if (_stateFlow.value !is ItemSetState.Paused) {
            logW("Cannot resume a set that is not paused")
            return
        }

        if (set == null) {
            logE("Cannot resume a set without a set, call start first")
            return
        }

        val repetitionsDone = repetitionsDone ?: 0u
        val progress = computeProgress(repetitionsDone)

        val nextState: ItemSetState = if (repetitionGoal == null) {
            ItemSetState.Started(
                setData = ItemSetData.Repetitions(
                    repetitionsDone = 0u,
                    repetitionGoal = repetitionGoal,
                    progress = progress,
                ),
            )
        } else {
            ItemSetState.Running(
                setData = ItemSetData.Repetitions(
                    repetitionsDone = repetitionsDone,
                    repetitionGoal = repetitionGoal,
                    progress = progress,
                ),
            )
        }

        _stateFlow.value = nextState
    }

    override fun setInputValue(value: ItemSetWithInputValue) {
        if (value !is ItemSetWithInputValue.Repetitions) {
            logE("Unsupported input value type for $tag: ${value::class.simpleName}")
            return
        }

        if (!stateFlow.value.isRunning) {
            logE("Cannot set repetitions while set is not running")
            return
        }

        if (set == null) {
            logE("Cannot set repetitions without a set, call start first")
            return
        }

        val nextState = if (value.repetitions < (repetitionGoal ?: 0u)) {
            ItemSetState.Running(
                setData = ItemSetData.Repetitions(
                    repetitionsDone = value.repetitions,
                    repetitionGoal = repetitionGoal,
                    progress = computeProgress(value.repetitions),
                ),
            )
        } else {
            ItemSetState.Finished(
                setData = ItemSetData.Repetitions(
                    repetitionsDone = value.repetitions,
                    repetitionGoal = repetitionGoal,
                    progress = Progress.COMPLETE,
                ),
            )
        }

        _stateFlow.value = nextState
    }

    fun setInputValue(value: UInt) {
        setInputValue(ItemSetWithInputValue.Repetitions(value))
    }

    private fun computeProgress(repetitionsDone: UInt): Progress {
        if (repetitionsDone == 0u) {
            return Progress.ZERO
        }

        val goal = repetitionGoal ?: return Progress.COMPLETE
        val progressValue = repetitionsDone.toFloat() / goal.toFloat()

        return Progress(progressValue)
    }
}
