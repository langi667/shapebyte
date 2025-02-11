package de.stefan.lang.foundationUI.viewmodel

import kotlinx.coroutines.flow.StateFlow

interface UIStateProvider {
    val state: StateFlow<UIState>
}
