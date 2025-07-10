package de.stefan.lang.shapebyte.featureTogglesData

import de.stefan.lang.coreutils.api.logging.Loggable
import de.stefan.lang.coreutils.api.logging.Logging

class FeatureToggleRepository(
    override val logger: Logging,
    internal val defaultFeatureTogglesDatasource: FeatureToggleDatasource,
) : Loggable {
    suspend fun fetchFeatureToggle(identifier: String) = defaultFeatureTogglesDatasource.fetchFeatureToggle(identifier)
}
