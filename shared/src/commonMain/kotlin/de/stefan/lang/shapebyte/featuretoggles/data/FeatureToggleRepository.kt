package de.stefan.lang.shapebyte.featuretoggles.data

import de.stefan.lang.shapebyte.utils.Loggable
import de.stefan.lang.shapebyte.utils.Logging

class FeatureToggleRepository(
    override val logger: Logging,
    private val datasource: FeatureToggleDatasource,
) : Loggable {
    // TODO: Test
    fun fetchFeatureToggle(identifier: String) = datasource.fetchFeatureToggle(identifier)
}
