package de.stefan.lang.shapebyte.features.workout.domain.contract.item

import de.stefan.lang.coreutils.contract.progress.Progress
import de.stefan.lang.shapebyte.features.workout.data.contract.item.Item

public sealed class ItemsExecutionState {
    public data object Idle : ItemsExecutionState()

    public sealed class Launched(public val progress: Progress) : ItemsExecutionState()
    public data object Started : Launched(progress = Progress.ZERO)

    /**
     * @param item The item/exercise is currently running
     * @param itemIndex The index of item/exercise which is currently executed
     * @param itemCount The overall number of items which are executed
     * @param itemState The specific state of the current executed item
     * @param totalProgress The overall progress
     */
    public data class Running(
        public val item: Item,
        public val itemIndex: Int,
        public val itemCount: Int,
        public val itemState: ItemExecutionState.Running<*>,
        public val totalProgress: Progress,
    ) : Launched(totalProgress)

    public class Paused(progress: Progress) : Launched(progress = progress)
    public data class Finished(val completed: Boolean) : ItemsExecutionState()
}
