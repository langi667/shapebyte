package de.stefan.lang.shapebyte.shared.featuretoggles.data

import de.stefan.lang.core.logging.Loggable
import de.stefan.lang.core.logging.Logging

class FeatureToggleRepository(
    override val logger: Logging,
    internal val defaultFeatureTogglesDatasource: FeatureToggleDatasource,
) : Loggable {
    suspend fun fetchFeatureToggle(identifier: String) = defaultFeatureTogglesDatasource.fetchFeatureToggle(identifier)
}
