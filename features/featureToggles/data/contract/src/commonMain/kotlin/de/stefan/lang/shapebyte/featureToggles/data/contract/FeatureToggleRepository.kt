package de.stefan.lang.shapebyte.featureToggles.data.contract

import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logger

// TODO: create Interface
public class FeatureToggleRepository(
    override val logger: Logger,
    internal val dataSource: FeatureToggleDatasource,
) : Loggable {
    // TODO: must use load state
    public suspend fun fetchFeatureToggle(identifier: String): LoadState.Result<FeatureToggle> = dataSource.fetchFeatureToggle(identifier)
}
