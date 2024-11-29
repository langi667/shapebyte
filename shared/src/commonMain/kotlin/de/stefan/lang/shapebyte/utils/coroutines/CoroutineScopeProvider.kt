package de.stefan.lang.shapebyte.utils.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

class CoroutineScopeProvider : CoroutineScopeProviding {
    override fun createCoroutineScope(context: CoroutineContext): CoroutineScope {
        return CoroutineScope(context)
    }
}
