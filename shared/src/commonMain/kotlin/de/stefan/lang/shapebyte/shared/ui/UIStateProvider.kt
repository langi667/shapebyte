package de.stefan.lang.shapebyte.shared.ui

import kotlinx.coroutines.flow.StateFlow

interface UIStateProvider {
    val state: StateFlow<UIState>
}
