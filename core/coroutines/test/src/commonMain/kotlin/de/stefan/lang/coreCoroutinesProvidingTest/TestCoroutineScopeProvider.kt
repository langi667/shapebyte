package de.stefan.lang.coreCoroutinesProvidingTest

import de.stefan.lang.coroutines.api.CoroutineScopeProviding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.TestScope
import kotlin.coroutines.CoroutineContext

object TestCoroutineScopeProvider : CoroutineScopeProviding {
    override fun createCoroutineScope(context: CoroutineContext): CoroutineScope {
        return TestScope(context)
    }
}
