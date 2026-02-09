package de.stefan.lang.foundation.presentation.contract.event

import kotlinx.coroutines.flow.SharedFlow

public interface UIEventProvider {
    public val eventFlow: SharedFlow<UIEvent>
}

// TODO: join with UIEventProvider once refactoring is done
public interface UIEventUpdater {
    public fun emitEvent(event: UIEvent)
}