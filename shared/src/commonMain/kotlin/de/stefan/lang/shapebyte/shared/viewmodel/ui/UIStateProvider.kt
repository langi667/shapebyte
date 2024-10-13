package de.stefan.lang.shapebyte.shared.viewmodel.ui

import kotlinx.coroutines.flow.StateFlow

interface UIStateProvider {
    val state: StateFlow<UIState>
}
