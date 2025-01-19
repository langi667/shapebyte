package de.stefan.lang.shapebyte.features.workout.item.core.domain

import de.stefan.lang.shapebyte.features.workout.item.core.data.Item
import de.stefan.lang.shapebyte.utils.Progress

sealed class ItemsExecutionState {
    data object Idle : ItemsExecutionState()

    sealed class Launched(val progress: Progress) : ItemsExecutionState()
    data object Started : Launched(progress = Progress.ZERO)

    /**
     * @param item The item/exercise is currently running
     * @param itemIndex The index of item/exercise which is currently executed
     * @param itemCount The overall number of items which are executed
     * @param itemState The specific state of the current executed item
     * @param totalProgress The overall progress
     */
    data class Running(
        val item: Item,
        val itemIndex: Int,
        val itemCount: Int,
        val itemState: ItemExecutionState.Running<*>,
        val totalProgress: Progress,
    ) : Launched(totalProgress)

    data object Paused : ItemsExecutionState()
    data object Finished : ItemsExecutionState()
}
