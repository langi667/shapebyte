package de.stefan.lang.shapebyte.shared.ui

sealed class UIState {
    object Idle : UIState()
    object Loading : UIState()

    sealed class Content : UIState()

    // TODO: add error
    // TODO: add refreshing with optional data
    data class Data<T>(val data: T) : Content()
}
