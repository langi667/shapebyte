package de.stefan.lang.shapebyte.featureTogglesData

import de.stefan.lang.foundationCore.api.loadstate.LoadState

interface FeatureToggleDatasource {
    suspend fun fetchFeatureToggle(identifier: String): LoadState.Result<FeatureToggle>
}
