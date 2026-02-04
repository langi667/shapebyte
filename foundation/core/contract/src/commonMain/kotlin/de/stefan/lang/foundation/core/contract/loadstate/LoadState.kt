package de.stefan.lang.foundation.core.contract.loadstate

public sealed interface LoadState<out T> {
    public data object Loading : LoadState<Nothing>

    public sealed interface Result<out T> : LoadState<T>

    public data class Success<T>(public val data: T) : Result<T>
    public data class Error(public val reason: Throwable) : Result<Nothing>

    public val isLoading: Boolean
        get() = this is Loading

    @Suppress("UNCHECKED_CAST")
    public fun <T> dataOrNull(): T? {
        val data = (this as? Success<T>)?.data
        return data
    }

    @Suppress("UNCHECKED_CAST")
    public fun <T> resultOrNull(): Result<T>? = (this as? Result<T>)

    public fun errorOrNull(): Throwable? {
        return (this as? Error)?.reason
    }
}
