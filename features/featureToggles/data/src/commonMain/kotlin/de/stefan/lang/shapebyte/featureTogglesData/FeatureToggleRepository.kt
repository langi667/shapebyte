package de.stefan.lang.shapebyte.featureTogglesData

import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logging

// TODO: create Interface
class FeatureToggleRepository(
    override val logger: Logging,
    internal val dataSource: FeatureToggleDatasource,
) : Loggable {
    // TODO: must use load state
    suspend fun fetchFeatureToggle(identifier: String) = dataSource.fetchFeatureToggle(identifier)
}
