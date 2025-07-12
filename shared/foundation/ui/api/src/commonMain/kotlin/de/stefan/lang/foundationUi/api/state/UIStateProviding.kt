package de.stefan.lang.foundationUi.api.state

import kotlinx.coroutines.flow.StateFlow

interface UIStateProviding {
    val state: StateFlow<UIState>
}
