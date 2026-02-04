package de.stefan.lang.foundation.core.implementation.safearea

import de.stefan.lang.foundation.core.contract.safearea.SafeArea
import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

public actual class SafeAreaDetector actual constructor(logger: Logger) : Loggable {
    public actual override val logger: Logger = logger

    public actual fun detectSafeArea(): Flow<SafeArea> {
        return flowOf(SafeArea())
    }
}
