package de.stefan.lang.shapebyte.featureTogglesData

import de.stefan.lang.coreutils.contract.logging.Loggable
import de.stefan.lang.coreutils.contract.logging.Logging

class FeatureToggleRepository(
    override val logger: Logging,
    internal val dataSource: FeatureToggleDatasource,
) : Loggable {
    // TODO: must use load state
    suspend fun fetchFeatureToggle(identifier: String) = dataSource.fetchFeatureToggle(identifier)
}
