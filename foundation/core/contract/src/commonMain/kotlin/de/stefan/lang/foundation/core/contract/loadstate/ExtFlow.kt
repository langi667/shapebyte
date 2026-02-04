package de.stefan.lang.foundation.core.contract.loadstate

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

public fun <T> Flow<LoadState<T>>.asResultFlow(): Flow<LoadState.Result<T>> = this.mapNotNull {
    it.resultOrNull<T>()
}

public fun <T> Flow<LoadState<T>>.asDataFlow(): Flow<T> = this.mapNotNull {
    it.dataOrNull<T>()
}
