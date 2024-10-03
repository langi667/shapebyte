package de.stefan.lang.shapebyte.features.workout.domain

import de.stefan.lang.shapebyte.features.workout.data.ItemSet
import de.stefan.lang.shapebyte.features.workout.data.ItemSetState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface ItemSetHandling {
    val stateFlow: StateFlow<ItemSetState>

    fun start(set: ItemSet, scope: CoroutineScope)
    fun pause()
    fun resume(resumeScope: CoroutineScope)
}
