@file:Suppress("UNCHECKED_CAST")

package de.stefan.lang.shapebyte.features.workout.domain.contract.item

import de.stefan.lang.shapebyte.features.workout.data.contract.item.Item
import de.stefan.lang.shapebyte.features.workout.data.contract.item.ItemSet
import de.stefan.lang.utils.logging.contract.Loggable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
// TODO: rename to ItemExecution
public sealed interface ItemExecuting<out STATE_DATA_CLASS, out ITEM_SET_CLASS : ItemSet> :
    Loggable {
    public val item: Item
    public val sets: List<ITEM_SET_CLASS>

    public val state: StateFlow<ItemExecutionState<STATE_DATA_CLASS>>
    public val isRunning: Boolean
        get() = state.value is ItemExecutionState.Running || state.value is ItemExecutionState.Started
    public val isPaused: Boolean get() = state.value is ItemExecutionState.Paused

    public val currentSet: ITEM_SET_CLASS?
        get() = when (val currState = state.value) {
            is ItemExecutionState.Running -> currState.set as ITEM_SET_CLASS
            else -> null
        }

    public fun start(scope: CoroutineScope): Boolean
    public fun pause(): Boolean
    public fun stop(): Boolean
}

public interface ItemValueExecuting<
    out STATE_DATA_CLASS,
    out ITEM_SET_CLASS : ItemSet,
    ITEM_SET_WITH_INPUT_VALUE,
    > : ItemExecuting<STATE_DATA_CLASS, ITEM_SET_CLASS> {

    public fun setInputValue(value: ITEM_SET_WITH_INPUT_VALUE)
}

public interface TimedItemExecution : ItemExecuting<TimedItemExecutionData, ItemSet.Timed.Seconds>
public interface RepetitiveItemExecution :
    ItemValueExecuting<RepetitiveItemExecutionData, ItemSet.Repetition, UInt>
