package de.stefan.lang.coroutines.implementation

import de.stefan.lang.coroutines.contract.CoroutineScopeProvider
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

public class CoroutineScopeProviderImpl : CoroutineScopeProvider {
    override fun createCoroutineScope(context: CoroutineContext): CoroutineScope {
        return CoroutineScope(context)
    }
}
