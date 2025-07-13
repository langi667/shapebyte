package de.stefan.lang.foundationPresentation.api.state

import kotlinx.coroutines.flow.StateFlow

interface UIStateProviding {
    val state: StateFlow<UIState>
}
