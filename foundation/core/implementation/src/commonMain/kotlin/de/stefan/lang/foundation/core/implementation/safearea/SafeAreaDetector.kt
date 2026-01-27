package de.stefan.lang.foundation.core.implementation.safearea

import de.stefan.lang.foundation.core.contract.safearea.SafeArea
import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logging
import kotlinx.coroutines.flow.Flow

expect class SafeAreaDetector(logger: Logging) : Loggable {
    override val logger: Logging

    fun detectSafeArea(): Flow<SafeArea>
}
