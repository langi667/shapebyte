package de.stefan.lang.shapebyte.features.workout.data

sealed class ItemSetState {
    data object Idle : ItemSetState()
    class Started(val setData: ItemSetData) : ItemSetState()
    class Running(val setData: ItemSetData) : ItemSetState()
    class Paused(val setData: ItemSetData) : ItemSetState()
    data object Finished : ItemSetState()

    val isStopped: Boolean
        get() = this is Idle || this is Finished
}
