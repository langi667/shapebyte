package de.stefan.lang.testcore

import de.stefan.lang.coreutils.coroutines.CoroutineScopeProviding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.TestScope
import kotlin.coroutines.CoroutineContext

object TestCoroutineScopeProvider : CoroutineScopeProviding {
    override fun createCoroutineScope(context: CoroutineContext): CoroutineScope {
        return TestScope(context)
    }
}
