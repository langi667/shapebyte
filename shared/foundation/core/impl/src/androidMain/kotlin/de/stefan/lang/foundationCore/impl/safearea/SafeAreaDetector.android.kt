package de.stefan.lang.foundationCore.impl.safearea

import de.stefan.lang.coreutils.api.Loggable
import de.stefan.lang.coreutils.api.Logging
import de.stefan.lang.foundationCore.api.safearea.SafeArea
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

actual class SafeAreaDetector actual constructor(logger: Logging) : Loggable {
    actual override val logger: Logging = logger

    actual fun detectSafeArea(): Flow<SafeArea> {
        return flowOf(SafeArea())
    }
}
