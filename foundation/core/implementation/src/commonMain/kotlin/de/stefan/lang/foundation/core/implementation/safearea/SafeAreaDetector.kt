package de.stefan.lang.foundation.core.implementation.safearea

import de.stefan.lang.foundation.core.contract.safearea.SafeArea
import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logger
import kotlinx.coroutines.flow.Flow

public expect class SafeAreaDetector public constructor(logger: Logger) : Loggable {
    public override val logger: Logger

    public fun detectSafeArea(): Flow<SafeArea>
}
