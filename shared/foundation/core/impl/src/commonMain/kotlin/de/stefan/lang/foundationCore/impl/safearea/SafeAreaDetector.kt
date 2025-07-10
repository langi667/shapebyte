package de.stefan.lang.foundationCore.impl.safearea

import de.stefan.lang.coreutils.api.Loggable
import de.stefan.lang.coreutils.api.Logging
import de.stefan.lang.foundationCore.api.safearea.SafeArea
import kotlinx.coroutines.flow.Flow

expect class SafeAreaDetector(logger: Logging) : Loggable {
    override val logger: Logging

    fun detectSafeArea(): Flow<SafeArea>
}
