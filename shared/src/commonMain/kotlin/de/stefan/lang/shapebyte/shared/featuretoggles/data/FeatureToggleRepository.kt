package de.stefan.lang.shapebyte.shared.featuretoggles.data

import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging

class FeatureToggleRepository(
    override val logger: Logging,
    internal val datasource: FeatureToggleDatasource,
) : Loggable {
    // TODO: Test
    fun fetchFeatureToggle(identifier: String) = datasource.fetchFeatureToggle(identifier)
}
