package de.stefan.lang.shapebyte.features.workout.item.shared.domain

import de.stefan.lang.shapebyte.features.workout.item.shared.data.Item
import de.stefan.lang.shapebyte.features.workout.item.shared.data.ItemSet
import de.stefan.lang.shapebyte.utils.Progress

sealed interface ItemExecutionState<out T> {
    data object Idle : ItemExecutionState<Nothing>

    interface ItemContaining<T> : ItemExecutionState<T> {
        val item: Item
    }

    data class Started(override val item: Item) : ItemContaining<Nothing>

    interface Running<T> : ItemContaining<T> {
        override val item: Item
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
