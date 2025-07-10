package de.stefan.lang.foundationUi.api.viewmodel

import kotlinx.coroutines.flow.StateFlow

interface UIStateProvider {
    val state: StateFlow<UIState>
}
