package de.stefan.lang.shapebyte.features.workout.item.domain

import de.stefan.lang.shapebyte.features.workout.item.data.ItemSetData
import de.stefan.lang.shapebyte.utils.Progress

sealed interface ItemSetsState {
    data object Idle : ItemSetsState
    data class Started(val totalSets: Int) : ItemSetsState

    sealed interface Running : ItemSetsState {
        val currentSetIndex: Int
        val totalSets: Int
        val setData: ItemSetData

        data class SetStarted(
            override val currentSetIndex: Int,
            override val totalSets: Int,
            override val setData: ItemSetData,
        ) : Running

        data class SetRunning(
            override val currentSetIndex: Int,
            override val totalSets: Int,
            val currentSetProgress: Progress,
            val totalProgress: Progress,
            override val setData: ItemSetData,
        ) : Running

        data class SetFinished(
            override val currentSetIndex: Int,
            override val totalSets: Int,
            override val setData: ItemSetData,
        ) : Running
    }

    data object Paused : ItemSetsState
    data object Finished : ItemSetsState

    val isRunning: Boolean
        get() {
            return this is Running || this is Started
        }
}
