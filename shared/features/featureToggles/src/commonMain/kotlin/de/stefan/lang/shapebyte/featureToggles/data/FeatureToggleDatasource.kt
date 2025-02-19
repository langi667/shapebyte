package de.stefan.lang.shapebyte.featureToggles.data

import de.stefan.lang.foundationCore.loadstate.LoadState

interface FeatureToggleDatasource {
    suspend fun fetchFeatureToggle(identifier: String): LoadState.Result<FeatureToggle>
}
