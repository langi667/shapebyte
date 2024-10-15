package de.stefan.lang.shapebyte.shared.viewmodel.ui

sealed class UIState {
    data object Idle : UIState()
    data object Loading : UIState()

    sealed class Content : UIState()

    // TODO: add error
    // TODO: add refreshing with optional data
    data class Data<T>(val data: T) : Content()
}