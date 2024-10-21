package de.stefan.lang.shapebyte.features.workout.item.domain

import de.stefan.lang.shapebyte.features.workout.item.data.ItemSet
import de.stefan.lang.shapebyte.features.workout.item.data.ItemSetState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

// TODO: Implement correctly
class DefaultItemSetHandler : ItemSetHandling {
    override val stateFlow: StateFlow<ItemSetState>
        get() = TODO("Not yet implemented")

    override fun start(set: ItemSet, scope: CoroutineScope) {
        TODO("Not yet implemented")
    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun resume(resumeScope: CoroutineScope) {
        TODO("Not yet implemented")
    }
}
