package de.stefan.lang.shapebyte.shared.featuretoggles.data

import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging

class FeatureToggleRepository(
    override val logger: Logging,
    internal val defaultFeatureTogglesDatasource: FeatureToggleDatasource,
) : Loggable {
    suspend fun fetchFeatureToggle(identifier: String) = defaultFeatureTogglesDatasource.fetchFeatureToggle(identifier)
}
