package de.stefan.lang.shapebyte.features.workout.item.domain

import de.stefan.lang.shapebyte.features.workout.item.data.Item
import de.stefan.lang.shapebyte.features.workout.item.data.ItemSet
import de.stefan.lang.shapebyte.utils.Progress
import de.stefan.lang.shapebyte.utils.logging.Loggable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

sealed interface ItemExecutionState<out T> {
    data object Idle : ItemExecutionState<Nothing>

    data class Started(val item: Item) : ItemExecutionState<Nothing>

    interface Running<T> : ItemExecutionState<T> {
        val item: Item
        val set: ItemSet
        val setProgress: Progress
        val totalProgress: Progress
        val setData: T
    }

    data class SetStarted<T>(
        override val item: Item,
        override val set: ItemSet,
        override val setProgress: Progress,
        override val totalProgress: Progress,
        override val setData: T,
    ) : Running<T>

    data class SetRunning<T>(
        override val item: Item,
        override val set: ItemSet,
        override val setProgress: Progress,
        override val totalProgress: Progress,
        override val setData: T,
    ) : Running<T>

    data class SetFinished<T>(
        override val item: Item,
        override val set: ItemSet,
        override val setProgress: Progress,
        override val totalProgress: Progress,
        override val setData: T,
    ) : Running<T>

    // TODO: maybe paused state
    data class Finished(val item: Item) : ItemExecutionState<Nothing>
}

interface ItemExecuting<out STATE_DATA_CLASS, out ITEM_SET_CLASS : ItemSet> :
    Loggable {
    val item: Item
    val sets: List<ITEM_SET_CLASS>

    val state: StateFlow<ItemExecutionState<STATE_DATA_CLASS>>

    fun start(scope: CoroutineScope): Boolean

    // TODO: maybe pause()
    // TODO: maybe stop/cancel
}
