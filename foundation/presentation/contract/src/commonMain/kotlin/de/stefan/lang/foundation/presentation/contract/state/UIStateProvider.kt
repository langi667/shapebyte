package de.stefan.lang.foundation.presentation.contract.state

import kotlinx.coroutines.flow.StateFlow

public interface UIStateProvider {
    public val state: StateFlow<UIState>
}

// TODO: join with UIStateProvider once refactoring is done
public interface UIStateUpdater {
    public fun updateState(newState: UIState)
}
