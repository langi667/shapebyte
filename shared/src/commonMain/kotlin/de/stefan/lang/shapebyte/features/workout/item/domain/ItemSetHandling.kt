package de.stefan.lang.shapebyte.features.workout.item.domain

import de.stefan.lang.shapebyte.features.workout.item.data.ItemSet
import de.stefan.lang.shapebyte.features.workout.item.data.ItemSetState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface ItemSetHandling {
    val stateFlow: StateFlow<ItemSetState>

    fun start(set: ItemSet, scope: CoroutineScope)
    fun pause()
    fun resume(resumeScope: CoroutineScope)
}
