package de.stefan.lang.shapebyte.shared.ui

import de.stefan.lang.shapebyte.utils.Loggable
import de.stefan.lang.shapebyte.utils.Logging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.cancel

actual open class BaseViewModel actual constructor(
    actual override val logger: Logging
) : Loggable {
    actual val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    fun clear() {
        scope.cancel()
    }
}