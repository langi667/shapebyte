package de.stefan.lang.shapebyte.features.workout.item.core.domain

import de.stefan.lang.shapebyte.features.workout.item.core.data.Item
import de.stefan.lang.shapebyte.utils.Progress

sealed class ItemsExecutionState {
    data object Idle : ItemsExecutionState()

    sealed class Launched(val progress: Progress) : ItemsExecutionState()
    data object Started : Launched(progress = Progress.ZERO)
    data class Running(
        val item: Item,
        val itemIndex: Int,
        val itemCount: Int,
        val itemState: ItemExecutionState.Running<*>,
        val totalProgress: Progress,
        val nextTotalProgress: Progress,
    ) : Launched(totalProgress)
    data object Finished : ItemsExecutionState()
}
