package de.stefan.lang.shapebyte.features.workout.domain.contract.item

import de.stefan.lang.utils.logging.contract.Loggable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface ItemsExecution : Loggable {
    val items: List<ItemExecuting<*, *>>
    val state: StateFlow<ItemsExecutionState>

    fun start(scope: CoroutineScope): Boolean
    fun pause(): Boolean
    fun stop(): Boolean
    fun pauseOrStart(scope: CoroutineScope): Boolean
}
