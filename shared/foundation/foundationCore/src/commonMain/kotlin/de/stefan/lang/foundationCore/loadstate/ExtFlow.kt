package de.stefan.lang.foundationCore.loadstate

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

fun <T> Flow<LoadState<T>>.asResultFlow() = this.mapNotNull {
    it.resultOrNull<T>()
}

fun <T> Flow<LoadState<T>>.asDataFlow() = this.mapNotNull {
    it.dataOrNull() as? T
}
