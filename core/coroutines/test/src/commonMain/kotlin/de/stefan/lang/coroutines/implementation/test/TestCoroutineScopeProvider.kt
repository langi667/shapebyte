package de.stefan.lang.coroutines.implementation.test

import de.stefan.lang.coroutines.contract.CoroutineScopeProviding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.TestScope
import kotlin.coroutines.CoroutineContext

public object TestCoroutineScopeProvider : CoroutineScopeProviding {
    override fun createCoroutineScope(context: CoroutineContext): CoroutineScope {
        return TestScope(context)
    }
}
