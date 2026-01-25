package de.stefan.lang.foundation.core.implementation.safearea

import de.stefan.lang.coreutils.contract.logging.Loggable
import de.stefan.lang.coreutils.contract.logging.Logging
import de.stefan.lang.foundation.core.contract.safearea.SafeArea
import kotlinx.coroutines.flow.Flow

expect class SafeAreaDetector(logger: Logging) : Loggable {
    override val logger: Logging

    fun detectSafeArea(): Flow<SafeArea>
}
