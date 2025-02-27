@file:Suppress("UNCHECKED_CAST")

package de.stefan.lang.shapebyte.features.workout.workoutDomain.item

import de.stefan.lang.coreutils.logging.Loggable
import de.stefan.lang.shapebyte.features.workout.workoutData.item.Item
import de.stefan.lang.shapebyte.features.workout.workoutData.item.ItemSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

sealed interface ItemExecuting<out STATE_DATA_CLASS, out ITEM_SET_CLASS : ItemSet> :
    Loggable {
    val item: Item
    val sets: List<ITEM_SET_CLASS>

    val state: StateFlow<ItemExecutionState<STATE_DATA_CLASS>>
    val isRunning: Boolean get() = state.value is ItemExecutionState.Running || state.value is ItemExecutionState.Started
    val isPaused: Boolean get() = state.value is ItemExecutionState.Paused

    val currentSet: ITEM_SET_CLASS? get() = when (val currState = state.value) {
        is ItemExecutionState.Running -> currState.set as ITEM_SET_CLASS
        else -> null
    }

    fun start(scope: CoroutineScope): Boolean
    fun pause(): Boolean
    fun stop(): Boolean
}

interface ItemValueExecuting<
    out STATE_DATA_CLASS,
    out ITEM_SET_CLASS : ItemSet,
    ITEM_SET_WITH_INPUT_VALUE,
    > : ItemExecuting<STATE_DATA_CLASS, ITEM_SET_CLASS> {

    fun setInputValue(value: ITEM_SET_WITH_INPUT_VALUE)
}

interface TimedItemExecuting : ItemExecuting<TimedItemExecutionData, ItemSet.Timed.Seconds>
interface RepetitiveItemExecuting :
    ItemValueExecuting<RepetitiveItemExecutionData, ItemSet.Repetition, UInt>
