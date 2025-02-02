package de.stefan.lang.core.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

interface CoroutineScopeProviding {
    fun createCoroutineScope(context: CoroutineContext): CoroutineScope
}
