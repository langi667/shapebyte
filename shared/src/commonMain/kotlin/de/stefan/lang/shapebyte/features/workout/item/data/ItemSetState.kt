package de.stefan.lang.shapebyte.features.workout.item.data

sealed interface ItemSetState {
    data object Idle : ItemSetState

    sealed interface Data : ItemSetState {
        val setData: ItemSetData
    }

    data class Started(override val setData: ItemSetData) : Data
    data class Running(override val setData: ItemSetData) : Data
    data class Paused(override val setData: ItemSetData) : Data
    data class Finished(override val setData: ItemSetData) : Data

    val isStopped: Boolean
        get() = this is Idle || this is Finished

    val isRunning: Boolean
        get() = this is Started || this is Running
}
