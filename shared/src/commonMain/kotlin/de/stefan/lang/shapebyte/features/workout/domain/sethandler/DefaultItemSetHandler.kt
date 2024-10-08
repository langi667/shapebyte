package de.stefan.lang.shapebyte.features.workout.domain.sethandler

import de.stefan.lang.shapebyte.features.workout.data.ItemSet
import de.stefan.lang.shapebyte.features.workout.data.ItemSetState
import de.stefan.lang.shapebyte.features.workout.domain.ItemSetHandling
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
