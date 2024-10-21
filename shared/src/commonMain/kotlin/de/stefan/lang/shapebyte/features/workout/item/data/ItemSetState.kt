package de.stefan.lang.shapebyte.features.workout.item.data

sealed class ItemSetState {
    data object Idle : ItemSetState()
    data class Started(val setData: ItemSetData) : ItemSetState()
    data class Running(val setData: ItemSetData) : ItemSetState()
    data class Paused(val setData: ItemSetData) : ItemSetState()
    data class Finished(val setData: ItemSetData) : ItemSetState()

    val isStopped: Boolean
        get() = this is Idle || this is Finished
}
