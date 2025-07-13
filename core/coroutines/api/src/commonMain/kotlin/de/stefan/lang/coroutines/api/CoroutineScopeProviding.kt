package de.stefan.lang.coroutines.api

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

public interface CoroutineScopeProviding {
    fun createCoroutineScope(context: CoroutineContext): CoroutineScope
}
