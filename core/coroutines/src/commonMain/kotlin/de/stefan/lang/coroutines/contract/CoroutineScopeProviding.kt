package de.stefan.lang.coroutines.contract

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

public interface CoroutineScopeProviding {
    public fun createCoroutineScope(context: CoroutineContext): CoroutineScope
}
