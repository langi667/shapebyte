package de.stefan.lang.coreCoroutinesProviding

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

interface CoroutineScopeProviding {
    fun createCoroutineScope(context: CoroutineContext): CoroutineScope
}
