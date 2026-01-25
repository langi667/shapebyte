package de.stefan.lang.foundation.presentation.contract.state

import kotlinx.coroutines.flow.StateFlow

interface UIStateProviding {
    val state: StateFlow<UIState>
}
