package de.stefan.lang.foundation.core.implementation.safearea

import de.stefan.lang.foundation.core.contract.safearea.SafeArea
import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logger
import kotlinx.coroutines.flow.Flow

expect class SafeAreaDetector(logger: Logger) : Loggable {
    override val logger: Logger

    fun detectSafeArea(): Flow<SafeArea>
}
