package de.stefan.lang.coroutines

import de.stefan.lang.coroutines.api.CoroutineScopeProviding
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

class CoroutineScopeProvider : CoroutineScopeProviding {
    override fun createCoroutineScope(context: CoroutineContext): CoroutineScope {
        return CoroutineScope(context)
    }
}
