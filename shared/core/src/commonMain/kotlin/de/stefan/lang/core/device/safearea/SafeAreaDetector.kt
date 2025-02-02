package de.stefan.lang.core.device.safearea

import de.stefan.lang.core.logging.Loggable
import de.stefan.lang.core.logging.Logging
import kotlinx.coroutines.flow.Flow

expect class SafeAreaDetector(logger: Logging) : Loggable {
    override val logger: Logging

    fun detectSafeArea(): Flow<SafeArea>
}
