package de.stefan.lang.foundationUI.viewmodel

sealed class UIState {
    data object Idle : UIState()
    data object Loading : UIState()

    sealed class Content : UIState()

    // TODO: add error
    // TODO: add refreshing with optional data
    data class Data<T>(val data: T) : Content()

    inline fun <reified T> dataOrNull(): T? {
        return when (this) {
            is Data<*> -> data as? T
            else -> null
        }
    }

    inline fun <reified T> dataOrElse(elseBlock: () -> T): T {
        val data = dataOrNull<T>()
        return data ?: elseBlock()
    }
}
