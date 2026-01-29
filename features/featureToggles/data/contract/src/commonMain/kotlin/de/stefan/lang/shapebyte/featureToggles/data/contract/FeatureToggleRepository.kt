package de.stefan.lang.shapebyte.featureToggles.data.contract

import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logging

// TODO: create Interface
public class FeatureToggleRepository(
    override val logger: Logging,
    internal val dataSource: FeatureToggleDatasource,
) : Loggable {
    // TODO: must use load state
    public suspend fun fetchFeatureToggle(identifier: String): LoadState.Result<FeatureToggle> = dataSource.fetchFeatureToggle(identifier)
}
