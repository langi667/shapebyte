package de.stefan.lang.coroutines.contract

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

public interface CoroutineScopeProvider {
    public fun createCoroutineScope(context: CoroutineContext): CoroutineScope
}
