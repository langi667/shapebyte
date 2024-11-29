package de.stefan.lang.shapebyte.utils

import de.stefan.lang.shapebyte.utils.coroutines.CoroutineScopeProviding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.TestScope
import kotlin.coroutines.CoroutineContext

object TestCoroutineScopeProvider : CoroutineScopeProviding {
    override fun createCoroutineScope(context: CoroutineContext): CoroutineScope {
        return TestScope(context)
    }
}
