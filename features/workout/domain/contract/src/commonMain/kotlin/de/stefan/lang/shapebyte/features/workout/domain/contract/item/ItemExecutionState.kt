package de.stefan.lang.shapebyte.features.workout.domain.contract.item

import de.stefan.lang.coreutils.contract.progress.Progress
import de.stefan.lang.shapebyte.features.workout.data.contract.item.Item
import de.stefan.lang.shapebyte.features.workout.data.contract.item.ItemSet

public sealed interface ItemExecutionState<out T> {
    public data object Idle : ItemExecutionState<Nothing>

    public interface ItemContaining<out T> : ItemExecutionState<T> {
        public val item: Item
    }

    public data class Started(override val item: Item) : ItemContaining<Nothing>

    public interface Running<out T> : ItemContaining<T> {
        /**
         * The current item which sets are running
         */
        public override val item: Item

        /**
         * The current set of the item that is running
         */
        public val set: ItemSet

        /**
         * The progress of the current set
         */
        public val progress: Progress

        /**
         * Progress in relations to all sets, basically the overall progress
         */
        public val totalProgress: Progress

        /**
         * The specific set data, like [TimedItemExecutionData]
         */
        public val setData: T
    }

    public data class SetStarted<out T>(
        override val item: Item,
        override val set: ItemSet,
        override val progress: Progress,
        override val totalProgress: Progress,
        override val setData: T,
    ) : Running<T>

    public data class SetRunning<out T>(
        override val item: Item,
        override val set: ItemSet,
        override val progress: Progress,
        override val totalProgress: Progress,
        override val setData: T,
    ) : Running<T>

    public data class SetFinished<out T>(
        override val item: Item,
        override val set: ItemSet,
        override val progress: Progress,
        override val totalProgress: Progress,
        override val setData: T,
    ) : Running<T>

    public data class Paused(val item: Item) : ItemExecutionState<Nothing>
    public data class Finished(val item: Item, val completed: Boolean) : ItemExecutionState<Nothing>
}
