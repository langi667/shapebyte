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

    private val _stateFlow = MutableStateFlow<ItemSetState>(ItemSetState.Idle)

    override val stateFlow: StateFlow<ItemSetState>
        get() = _stateFlow

    override fun start(set: ItemSet, scope: CoroutineScope) {
        if (!stateFlow.value.isStopped) {
            logD("Cannot start a new set while another one is running")
            return
        }

        when (set) {
            set as ItemSet.Repetition -> this.set = set
            else -> {
                logE("Unsupported set type for $tag: ${set::class.simpleName}")
                return
            }
        }

        _stateFlow.value = ItemSetState.Started(
            setData = ItemSetData.Repetitions(0u, Progress.ZERO),
        )
    }

    override fun pause() {
        if (!_stateFlow.value.isRunning) {
            logW("Cannot pause a set that is not running")
            return
        }

        _stateFlow.value = ItemSetState.Paused(ItemSetData.Repetitions(repetitionsDone ?: 0u, Progress.ZERO))
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

        // TODO: could also be a running state if repetitions are not 0
        _stateFlow.value = ItemSetState.Started(ItemSetData.Repetitions(0u, Progress.ZERO))
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
            logE("Cannot set repetitions without a set, cass start first")
            return
        }

        _stateFlow.value = ItemSetState.Finished(ItemSetData.Repetitions(value.repetitions, Progress.COMPLETE))
    }

    fun setInputValue(value: UInt) {
        setInputValue(ItemSetWithInputValue.Repetitions(value))
    }
}
