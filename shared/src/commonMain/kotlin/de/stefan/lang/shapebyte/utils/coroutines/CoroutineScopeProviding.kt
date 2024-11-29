package de.stefan.lang.shapebyte.utils.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

interface CoroutineScopeProviding {
    fun createCoroutineScope(context: CoroutineContext): CoroutineScope
}
