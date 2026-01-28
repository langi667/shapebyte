package de.stefan.lang.foundation.presentation.contract.state

import kotlinx.coroutines.flow.StateFlow

public interface UIStateProviding {
    public val state: StateFlow<UIState>
}
