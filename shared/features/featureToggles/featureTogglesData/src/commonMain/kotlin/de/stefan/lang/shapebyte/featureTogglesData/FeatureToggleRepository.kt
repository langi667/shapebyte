package de.stefan.lang.shapebyte.featureTogglesData

import de.stefan.lang.coreutils.logging.Loggable
import de.stefan.lang.coreutils.logging.Logging

class FeatureToggleRepository(
    override val logger: Logging,
    internal val defaultFeatureTogglesDatasource: FeatureToggleDatasource,
) : Loggable {
    suspend fun fetchFeatureToggle(identifier: String) = defaultFeatureTogglesDatasource.fetchFeatureToggle(identifier)
}
