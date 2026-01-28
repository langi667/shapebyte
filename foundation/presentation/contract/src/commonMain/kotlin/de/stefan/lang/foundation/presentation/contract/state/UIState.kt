package de.stefan.lang.foundation.presentation.contract.state

public sealed class UIState {
    public data object Idle : UIState()
    public data object Loading : UIState()

    public sealed class Content : UIState()

    // TODO: add error
    // TODO: add refreshing with optional data
    public data class Data<T>(val data: T) : Content()

    public inline fun <reified T> dataOrNull(): T? {
        return when (this) {
            is Data<*> -> data as? T
            else -> null
        }
    }

    public inline fun <reified T> dataOrElse(elseBlock: () -> T): T {
        val data = dataOrNull<T>()
        return data ?: elseBlock()
    }
}
