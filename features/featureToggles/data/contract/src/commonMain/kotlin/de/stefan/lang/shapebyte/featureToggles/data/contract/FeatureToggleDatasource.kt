package de.stefan.lang.shapebyte.featureToggles.data.contract

import de.stefan.lang.foundation.core.contract.loadstate.LoadState

public interface FeatureToggleDatasource {
    public suspend fun fetchFeatureToggle(identifier: String): LoadState.Result<FeatureToggle>
}
