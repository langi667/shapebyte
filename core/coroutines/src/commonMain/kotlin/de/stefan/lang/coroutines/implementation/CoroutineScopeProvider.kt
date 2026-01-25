package de.stefan.lang.coroutines.implementation

import de.stefan.lang.coroutines.contract.CoroutineScopeProviding
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

internal class CoroutineScopeProvider : CoroutineScopeProviding {
    override fun createCoroutineScope(context: CoroutineContext): CoroutineScope {
        return CoroutineScope(context)
    }
}
