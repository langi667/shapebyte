package de.stefan.lang.shapebyte.features.workout.domain.contract.item

import de.stefan.lang.coreutils.contract.progress.Progress
import de.stefan.lang.shapebyte.features.workout.data.contract.item.Item
import de.stefan.lang.shapebyte.features.workout.data.contract.item.ItemSet

sealed interface ItemExecutionState<out T> {
    data object Idle : ItemExecutionState<Nothing>

    interface ItemContaining<out T> : ItemExecutionState<T> {
        val item: Item
    }

    data class Started(override val item: Item) : ItemContaining<Nothing>

    interface Running<out T> : ItemContaining<T> {
        /**
         * The current item which sets are running
         */
        override val item: Item

        /**
         * The current set of the item that is running
         */
        val set: ItemSet

        /**
         * The progress of the current set
         */
        val progress: Progress

        /**
         * Progress in relations to all sets, basically the overall progress
         */
        val totalProgress: Progress

        /**
         * The specific set data, like [TimedItemExecutionData]
         */
        val setData: T
    }

    data class SetStarted<out T>(
        override val item: Item,
        override val set: ItemSet,
        override val progress: Progress,
        override val totalProgress: Progress,
        override val setData: T,
    ) : Running<T>

    data class SetRunning<out T>(
        override val item: Item,
        override val set: ItemSet,
        override val progress: Progress,
        override val totalProgress: Progress,
        override val setData: T,
    ) : Running<T>

    data class SetFinished<out T>(
        override val item: Item,
        override val set: ItemSet,
        override val progress: Progress,
        override val totalProgress: Progress,
        override val setData: T,
    ) : Running<T>

    data class Paused(val item: Item) : ItemExecutionState<Nothing>
    data class Finished(val item: Item, val completed: Boolean) : ItemExecutionState<Nothing>
}
