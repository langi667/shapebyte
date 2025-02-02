package de.stefan.lang.core.device.safearea

import de.stefan.lang.core.device.safearea.SafeArea
import de.stefan.lang.core.logging.Loggable
import de.stefan.lang.core.logging.Logging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

actual class SafeAreaDetector actual constructor(logger: Logging) : Loggable {
    actual override val logger: Logging = logger

    actual fun detectSafeArea(): Flow<SafeArea> {
        return flowOf(SafeArea())
    }
}
