package de.stefan.lang.foundationCore.api.loadstate

sealed interface LoadState<out T> {
    data object Loading : LoadState<Nothing>

    sealed interface Result<out T> : LoadState<T>

    data class Success<T>(val data: T) : Result<T>
    data class Error(val reason: Throwable) : Result<Nothing>

    val isLoading: Boolean
        get() = this is Loading

    @Suppress("UNCHECKED_CAST")
    fun <T> dataOrNull(): T? {
        val data = (this as? Success<T>)?.data
        return data
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> resultOrNull(): Result<T>? = (this as? Result<T>)

    fun errorOrNull(): Throwable? {
        return (this as? Error)?.reason
    }
}
