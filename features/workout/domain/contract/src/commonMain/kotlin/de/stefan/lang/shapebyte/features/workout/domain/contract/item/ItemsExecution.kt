package de.stefan.lang.shapebyte.features.workout.domain.contract.item

import de.stefan.lang.utils.logging.contract.Loggable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

public interface ItemsExecution : Loggable {
    public val items: List<ItemExecuting<*, *>>
    public val state: StateFlow<ItemsExecutionState>

    public fun start(scope: CoroutineScope): Boolean
    public fun pause(): Boolean
    public fun stop(): Boolean
    public fun pauseOrStart(scope: CoroutineScope): Boolean
}
