package de.stefan.lang.shapebyte.shared.loading.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

fun <T> Flow<LoadState<T>>.asDataFlow() = this.mapNotNull {
    it.dataOrNull() as? T
}