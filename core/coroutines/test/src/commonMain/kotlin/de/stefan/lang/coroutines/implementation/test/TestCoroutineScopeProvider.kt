package de.stefan.lang.coroutines.implementation.test

import de.stefan.lang.coroutines.contract.CoroutineScopeProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.TestScope
import kotlin.coroutines.CoroutineContext

public object TestCoroutineScopeProvider : CoroutineScopeProvider {
    override fun createCoroutineScope(context: CoroutineContext): CoroutineScope {
        return TestScope(context)
    }
}
